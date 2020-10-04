# Software Engineering 206 Assignment 3

TO START THE QUINZICAL GAME:
==========================================
1) right-click in the current directory.
2) select open in terminal
3) in the terminal, run the following:
./quinzical.sh
==========================================

*If permission failed. execute "chmod +x quinzical.sh" in the terminal*
*Make sure there are at least 5 .txt files in directory /categories/*
*Make sure there are at least 5 clues in each *.txt files in /categories/*.txt*
*Newly added clues in .txt file should be in the format of [{question}, {answer_part_one} {answer_part_two}], and one clue each line in .txt*
*Frequently clicking a button in very short period may leads to program shut down, please try to avoid that*

Submission zip file contains:
assignment-3-and-project-team-10/
	categories/
		Endeavour.txt
		...
		Symbols.txt
	scripts/
		get5RandomQuestion.sh
		getCategories.sh
		getRandomQuestion.sh
	src/
		quinzical/
			controller/
				Clue.java
				GameController.java
				...
				VoiceTask.java
			ui/	
				GameView.java
				...
				SettingsView.java
			Main.java
			style.css
	assignment3.jar
	quinzical.sh
	README.md

