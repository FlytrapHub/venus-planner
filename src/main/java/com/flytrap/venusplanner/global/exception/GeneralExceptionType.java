package com.flytrap.venusplanner.global.exception;

import org.springframework.http.HttpStatus;

public class GeneralExceptionType {

    /**
     * 일반적인 예외 상황에 사용되는 예외입니다.
     * <p>
     * 다른 패키지에 정의될 예외 중 공통 타입으로 묶이지 않는 예외는 이 GENERAL_EXCEPTION을 상속 받으면 됩니다.
     *
     * @param httpStatus HTTP 상태 코드
     * @param message 예외에 대한 메시지
     * @return CustomException 객체
     */
    public static CustomException GENERAL_EXCEPTION(HttpStatus httpStatus, String message) {
        return new CustomException(httpStatus, message);
    }

    /**
     * 어떤 도메인 클래스를 찾을 수 없을 때 사용되는 예외입니다.
     *
     * @param domainClazz 찾을 수 없는 도메인 클래스
     * @return CustomException 객체
     */
    public static CustomException DOMAIN_NOT_FOUND_EXCEPTION(Class<?> domainClazz) {
        return new CustomException(
                HttpStatus.NOT_FOUND,
                String.format("[%s] 도메인을 찾을 수 없습니다.", domainClazz.getSimpleName())
        );
    }

    /**
     * 주어진 ID를 가진 도메인 객체를 찾을 수 없을 때 사용되는 예외입니다.
     *
     * @param domainClazz 찾을 수 없는 도메인 클래스
     * @param domainId 찾을 수 없는 도메인 객체의 ID
     * @return CustomException 객체
     */
    public static CustomException DOMAIN_NOT_FOUND_EXCEPTION(Class<?> domainClazz, Long domainId) {
        return new CustomException(
                HttpStatus.NOT_FOUND,
                String.format("[id = %d]에 해당하는 [%s] 도메인을 찾을 수 없습니다.", domainId, domainClazz.getSimpleName())
        );
    }

    /**
     * 주어진 Enum 타입에 존재하지 않는 이름을 사용했을 때 발생하는 예외입니다.
     *
     * @param enumClazz 대상 Enum 클래스
     * @param mismatchedTypeName 존재하지 않는 Enum의 Type 이름
     * @return CustomException 객체
     */
    public static CustomException NO_SUCH_ENUM_TYPE_EXCEPTION(Class<? extends Enum<?>> enumClazz, String mismatchedTypeName) {
        return new CustomException(
                HttpStatus.NOT_FOUND,
                String.format("[%s]은(는) [%s]의 타입이 아닙니다.", mismatchedTypeName, enumClazz.getSimpleName())
        );
    }

    /**
     * 외부 API 요청이 실패했을 때 사용되는 예외입니다.
     *
     * @param message 예외에 대한 메시지
     * @return CustomException 객체
     */
    public static CustomException API_REQUEST_FAILURE_EXCEPTION(String message) {
        return new CustomException(HttpStatus.BAD_GATEWAY, message);
    }

    /**
     * 인증 실패 시 사용되는 예외입니다.
     *
     * @param message 예외에 대한 메시지
     * @return CustomException 객체
     */
    public static CustomException AUTHENTICATION_FAILURE_EXCEPTION(String message) {
        return new CustomException(HttpStatus.UNAUTHORIZED, message);
    }

    /**
     * 권한이 없는 접근 시 사용되는 예외입니다.
     *
     * @param message 예외에 대한 메시지
     * @return CustomException 객체
     */
    public static CustomException FORBIDDEN_EXCEPTION(String message) {
        return new CustomException(HttpStatus.FORBIDDEN, message);
    }

}
