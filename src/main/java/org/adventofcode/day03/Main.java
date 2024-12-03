package org.adventofcode.day03;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class Main {

	public static void main(final String[] args) throws IOException {
		// read the file
		final List<String> inputByLines = FileUtils.readLines(
			new File(Main.class.getResource("input.txt").getFile()),
			StandardCharsets.UTF_8
		);

		// go over the input line by line and collect the valid "mul" instructions
		// multiply the valid "mul" instructions and add them all up
		long sumOfAllMul = 0;
		final String regex = "mul\\(\\d{1,3},\\d{1,3}\\)"; // targets "mul(X,Y)", where X and Y are each 1-3 digit numbers
		for (final String inputByLine : inputByLines) {
			final Matcher mulMatcher = Pattern.compile(regex).matcher(inputByLine);
			final List<String> mulMatches = new ArrayList<>();
			while (mulMatcher.find()) {
				mulMatches.add(mulMatcher.group());
			}
			for (final String mulMatch : mulMatches) { // 'mulMatch' example: "mul(382,128)"
				final String twoNumbersSeparatedWithComma = mulMatch.substring("mul(".length(), mulMatch.length() - 1);
				final String[] split = twoNumbersSeparatedWithComma.split(",");
				sumOfAllMul += Long.parseLong(split[0]) * Long.parseLong(split[1]);
			}
		}
		System.out.println("sumOfAllMul = " + sumOfAllMul);
	}
}
