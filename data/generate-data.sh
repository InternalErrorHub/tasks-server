#!/bin/bash

# 1706552057.403080007

while IFS=";" read -r firstname lastname email password
do
  registerJson="{ \"firstname\": \"$firstname\", \"lastname\": \"$lastname\", \"email\": \"$email\", \"password\": \"$password\" }"
  curl -X POST http://localhost:8080/api/auth/register -H 'Content-Type: application/json' -d "$registerJson"
  echo ""

  loginJson="{ \"email\": \"$email\", \"password\": \"$password\" }"
  loginData=$(curl -X POST http://localhost:8080/api/auth/login -H 'Content-Type: application/json' -d "$loginJson")
  echo ""
  token=$(cut -c 11-70 <<< $loginData)
  createTaskJson="{ \"token\": \"$token\", \"name\": \"$firstname $lastname does the dishes\", \"details\": \"Doing the Dishes\", \"due\": \"1706552057.403080007\" }"
  curl -X POST http://localhost:8080/api/task/create -H 'Content-Type: application/json' -d "$createTaskJson"

  echo ""
  done < <(tail -n +2 test-users.csv)