package org.adventofcode.day02;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Main {

	public static void main(String[] args) throws IOException {
		// read the file
		final List<String> inputByLines = FileUtils.readLines(
			new File(Main.class.getResource("input.txt").getFile()),
			StandardCharsets.UTF_8
		);

		// put the content of the file into a list (1000 size), which consists of around 5-8 Integers
		final List<List<Integer>> input = new ArrayList<>(1000);
		inputByLines.forEach(line -> {
			final List<Integer> numbersInTheLine = new ArrayList<>(8);
			final String[] lineSplit = line.split(" ");
			for (final String numberInTheLine : lineSplit) {
				numbersInTheLine.add(Integer.parseInt(numberInTheLine));
			}
			input.add(numbersInTheLine);
		});

		/*
		Count how many reports (lines) are safe (the numbers inside are called levels). A report is safe if:
		The levels are either all increasing or all decreasing
		AND
		Any two adjacent levels differ by at least one and at most three.
		 */
		int safeReportCounter = 0;
		for (final List<Integer> report : input) {
			final int subtractionOfSecondFromFirstLevel = report.get(1) - report.getFirst();
			boolean isLevelsIncreasing = subtractionOfSecondFromFirstLevel > 0;
			for (int i = 0; i < report.size() - 1; i++) { // because we always look 1 element further
				final Integer currentLevel = report.get(i);
				final Integer nextLevel = report.get(i + 1);
				if (isLevelsIncreasing && currentLevel >= nextLevel) {
					break; // levels decrease but should increase -> unsafe
				}
				if (!isLevelsIncreasing && currentLevel < nextLevel) {
					break; // levels increase but should decrease -> unsafe
				}
				final int levelDifference = Math.abs(currentLevel - nextLevel);
				if (levelDifference < 1 || levelDifference > 3) {
					break; // levels difference are too small (0) or too big (more than 3) -> unsafe
				}
				if (i == report.size() - 2) {
					safeReportCounter++; // we did not stop the iteration of the levels and reached the end -> this report is safe
				}
			}
		}
		System.out.println("safeReportCounter = " + safeReportCounter);
	}
}
