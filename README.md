# Software Engineering 206 Project

TO START THE QUINZICAL GAME:
==========================================
1) right-click in the current directory.
2) select open in terminal
3) in the terminal, run the following:
./quinzical.sh
==========================================

*If program does not run, change java -jar --module-path PATH --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml assignment4.jar
where PATH should be the location of the javafx file.
*If permission failed. execute "chmod +x quinzical.sh" in the terminal*
*Make sure there are at least 5 .txt files in directory /categories/*/
*Make sure there are at least 5 clues in each *.txt files in /categories/*.txt*
*Newly added clues in .txt file should be in the format of [{question}, {answer_part_one} {answer_part_two}], and one clue each line in .txt*
*Clues can also  be added un quinzical program.*
*Frequently clicking a button in very short period may leads to program shut down, please try to avoid that*

Submission zip file contains:
assignment-4-and-project-team-10/
	categories/
		international/
			Animals.txt
			...
			Sports.txt
		nz/
			ENdeavour.txt
			...
			Symbols.txt
	scripts/
		generateGameData.sh
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
	gamedata/
		internationalUnlocked
		leaderboard
	assignment4.jar
	quinzical.sh
	README.md

