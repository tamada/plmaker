#! /bin/bash

branchName=$(git branch --show-current)

# if already created the pull request
result=$(gh pr list --json headRefName | jq "isempty(.[] | select(.headRefName == \"${branchName}\"))")
if [[ "$result" -eq "true" ]] ; then
    exit
fi

message="$(git log --date=iso --first-parent --reverse --pretty=format:"* %s (%h) %cd" HEAD...main)"

gh pr create --title $branchName --base main --head $branchName --body "$message"