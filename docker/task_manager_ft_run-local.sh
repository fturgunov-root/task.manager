#!/bin/sh
../setup-env.sh

docker-compose \
-f task_manager_ft_postgres.dev.yml \
up -d --force-recreate "${@}"
