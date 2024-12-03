.PHONY: test build run debug lint/fix infrastructure/up

infrastructure/up:
	docker-compose up -d database flyway

down:
	docker-compose down --remove-orphans

test:
	./gradlew test

install:
	./gradlew build --refresh-dependencies

build:
	./gradlew build

run/docker:
	docker-compose up --build api

run:
	./gradlew bootRun

debug:
	./gradlew bootRun --debug-jvm

lint/fix:
	./gradlew spotlessApply

migration/run:
	./gradlew flywayMigrate