
#!/bin/bash

echo "🏁 Script Start."
echo "👉 Pulling github repository..."
git pull origin release

echo "👉 Pulling backend Docker image..."
cd ..
cat github_token/github_token.txt | docker login ghcr.io -u crtEvent --password-stdin
docker pull ghcr.io/flytraphub/venus-planner-be:release

echo "👉 Starting Docker Compose..."
cd venus-planner-be/
sudo docker-compose up -d

echo "👉 Cleaning up unused Docker images..."
sudo docker image prune -a -f

echo "🫡  Script execution completed."
