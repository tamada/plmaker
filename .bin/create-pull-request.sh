#! /bin/bash

branchName=$(git branch --show-current)

# if already created the pull request
result=$(gh pr list --json headRefName | jq ".[].headRefName" | grep $branchName)
if [[ $? == 0 ]] ; then
    echo "already exist"
    exit
fi

message="$(git log --date=iso --first-parent --reverse --pretty=format:"* %s (%h) %cd" $branchName...main)"

exec gh pr create --title $branchName --base main --head $branchName --body "$message"