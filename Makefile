BUILD_ENV=dev
BUILD_TAG=dev
DOCKER_DIST_TAG=linkgem/linkgem-be

.PHONY: all
all: build push deploy

.PHONY: build
build:
	docker build \
	--platform linux/amd64 \
	--build-arg BUILD_ENV=${BUILD_ENV} \
	-t ${DOCKER_DIST_TAG}:${BUILD_TAG} .

.PHONY: push
push: 
	docker push ${DOCKER_DIST_TAG}:${BUILD_TAG}

.PHONY: run-local
run-local:
	gradle bootrun --args='--spring.profiles.active=local'

.PHONY: run-docker
run-docker:
	docker run -it -p 8080:8080 ${DOCKER_DIST_TAG}:${BUILD_TAG} --spring.profiles.active=local

.PHONY: deploy
deploy:
  # TODO EKS or other way in github action
  # docker \
	# -H ssh://ubuntu@ec2-3-36-126-52.ap-northeast-2.compute.amazonaws.com \
	# run -d -it \
	# -p 8089:80 \
	# ${DOCKER_DIST_TAG}:${BUILD_TAG}
