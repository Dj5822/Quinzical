#!/bin/bash

mkdir -p gamedata
touch gamedata/leaderboard

	
# initialise file values only if the files are empty.
[ -s gamedata/internationalUnlocked ] || echo "false" > gamedata/internationalUnlocked

