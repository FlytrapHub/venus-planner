package com.flytrap.venusplanner.api.auth_member.infrastructure.external;

import com.flytrap.venusplanner.global.auth.config.OAuthWebClientConfig.GitHubOAuthFormDataBuilder;
import com.flytrap.venusplanner.api.auth_member.exception.GitHubOAuthRequestException;
import com.flytrap.venusplanner.api.auth_member.infrastructure.dto.AccessTokenFromGitHub;
import com.flytrap.venusplanner.api.auth_member.infrastructure.dto.StandardizedUserResource;
import com.flytrap.venusplanner.api.auth_member.infrastructure.dto.UserEmailResourceFromGitHub;
import com.flytrap.venusplanner.api.auth_member.infrastructure.dto.UserResourceFromGitHub;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class GitHubOAuthProvider implements OAuthProvider {

    private final GitHubOAuthFormDataBuilder gitHubOAuthFormDataBuilder;
    private final WebClient gitHubAccessTokenClient;
    private final WebClient gitHubUserResourceClient;
    private final WebClient gitHubEmailResourceClient;

    /**
     * GitHub OAuth에서 회원 인증 후 회원 정보를 반환합니다.
     * <p>
     * @param code GitHub OAuth 인증 과정에서 얻은 인증 코드
     * @return 사용자 정보를 담고 있는 {@link StandardizedUserResource} 객체입니다.
     * @throws GitHubOAuthRequestException GitHub 요청 과정에서 오류가 발생했을 때 발생합니다.
     * 이는 네트워크 문제, 데이터 포맷 문제, 서버 측 오류 등 다양한 이유로 발생할 수 있습니다.
     */
    public StandardizedUserResource authenticateAndFetchUserResource(String code) {

        var accessToken = requestAccessToken(code);
        var userResource = requestUserResource(accessToken);

        return StandardizedUserResource.from(userResource);
    }

    /**
     * GitHub OAuth 서버로부터 액세스 토큰을 요청합니다.
     * <p>
     * 이 메서드는 매개변수로 주어진 인증 코드를 사용하여 GitHub OAuth 서버에 액세스 토큰을 요청합니다. 요청이 성공하면, 액세스 토큰이 담긴
     * {@link AccessTokenFromGitHub} 객체를 반환합니다. 요청 중 발생하는 모든 4xx 및 5xx HTTP 응답 코드는
     * {@link GitHubOAuthRequestException}을 발생시킵니다.
     * <p>
     * @param code GitHub OAuth 인증 과정에서 얻은 인증 코드
     * @return 액세스 토큰 정보를 담고 있는 {@link AccessTokenFromGitHub}
     * @throws GitHubOAuthRequestException GitHub OAuth 요청 과정에서 발생하는 모든 예외를 포함합니다.
     * 이는 네트워크 문제, 데이터 포맷 문제, 서버 측 오류 등을 포함할 수 있습니다.
     */
    private AccessTokenFromGitHub requestAccessToken(String code) {

        MultiValueMap<String, String> formData = gitHubOAuthFormDataBuilder.buildFormData(code);

        // TODO: GlobalExceptionHandler 에서 GitHubOAuthRequestException 처리하기
        return gitHubAccessTokenClient
                .post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(
                        statusCode -> statusCode.is4xxClientError() || statusCode.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(new GitHubOAuthRequestException(body))))
                .bodyToMono(AccessTokenFromGitHub.class)
                .doOnError(error -> log.error("GitHub에서 액세스 토큰을 요청하는 중 에러 발생", error))
                .onErrorMap(throwable -> new GitHubOAuthRequestException(throwable.getMessage()))
                .block();
    }

    /**
     * GitHub에서 사용자 정보를 요청합니다.
     * <p>
     * 이 메서드는 주어진 액세스 토큰을 사용하여 GitHub의 사용자 정보 리소스에 대한 GET 요청을 실행합니다. 요청이 성공하면,
     * {@link UserResourceFromGitHub} 객체를 반환합니다. 요청 중에 4xx 또는 5xx HTTP 상태 코드가 반환되는 경우,
     * {@link GitHubOAuthRequestException}을 발생시킵니다.
     * <p>
     *
     * @param accessToken GitHub OAuth를 통해 얻은 액세스 토큰, {@link AccessTokenFromGitHub} 객체입니다.
     * @return 사용자 정보를 담고 있는 {@link UserResourceFromGitHub} 객체입니다. 사용자 정보에 이메일이 포함됩니다.
     * @throws GitHubOAuthRequestException GitHub 요청 과정에서 오류가 발생했을 때 발생합니다.
     * 이는 네트워크 문제, 데이터 포맷 문제, 서버 측 오류 등 다양한 이유로 발생할 수 있습니다.
     */
    private UserResourceFromGitHub requestUserResource(AccessTokenFromGitHub accessToken) {

        return gitHubUserResourceClient
                .get()
                .header(HttpHeaders.AUTHORIZATION, accessToken.getHeadValue())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        statusCode -> statusCode.is4xxClientError() || statusCode.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new GitHubOAuthRequestException(body))))
                .bodyToMono(UserResourceFromGitHub.class)
                .flatMap(userResource -> {
                    if (userResource.isEmailEmpty()) {
                        return requestPrimaryUserEmailResource(accessToken)
                                .map(email -> {
                                    userResource.updateEmail(email);

                                    return userResource;
                                });
                    }

                    return Mono.just(userResource);
                })
                .doOnError(error -> log.error("GitHub에서 회원 정보를 요청하는 중 에러 발생", error))
                .onErrorMap(throwable -> new GitHubOAuthRequestException(throwable.getMessage()))
                .block();
    }

    /**
     * GitHub에서 사용자의 이메일 정보를 요청합니다.
     * <p>
     * 이 메서드는 주어진 액세스 토큰을 사용하여 GitHub의 사용자 이메일 정보 리소스에 대한 GET 요청을 실행합니다.
     * 요청이 성공하면, 이메일 정보 목록 중 첫 번째 이메일 주소를 문자열로 반환합니다. 만약 이메일 정보 목록이 비어 있으면,
     * 빈 문자열을 반환합니다. 요청 중에 4xx 또는 5xx HTTP 상태 코드가 반환되는 경우,
     * {@link GitHubOAuthRequestException}을 Mono.error()로 반환합니다.
     * <p>
     * GitHub에서 사용자 정보를 요청할 때 email은 비공개 설정일 경우 반환하지 않기 때문에 이 메서드로 email을 추가 요청해야 합니다.
     * <p>
     * @param accessToken GitHub OAuth를 통해 얻은 액세스 토큰, {@link AccessTokenFromGitHub} 객체입니다.
     * @return 사용자의 첫 번째 이메일 주소를 담고 있는 문자열의 Mono입니다. 이메일 목록이 비어 있을 경우 빈 문자열을 반환합니다.
     * @throws GitHubOAuthRequestException GitHub 요청 과정에서 오류가 발생했을 때 발생합니다.
     * 이는 네트워크 문제, 데이터 포맷 문제, 서버 측 오류 등 다양한 이유로 발생할 수 있습니다.
     * <p>
     */
    private Mono<String> requestPrimaryUserEmailResource(AccessTokenFromGitHub accessToken) {

        return gitHubEmailResourceClient
                .get()
                .header(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .header(HttpHeaders.AUTHORIZATION, accessToken.getHeadValue())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        statusCode -> statusCode.is4xxClientError() || statusCode.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new GitHubOAuthRequestException(body))))
                .bodyToMono(new ParameterizedTypeReference<List<UserEmailResourceFromGitHub>>() {})
                .map(emailList -> emailList.isEmpty() ? "" : emailList.get(0).email())
                .doOnError(error -> log.error("GitHub에서 이메일 정보를 요청하는 중 에러 발생", error))
                .onErrorMap(throwable -> new GitHubOAuthRequestException(throwable.getMessage()));
    }

}
