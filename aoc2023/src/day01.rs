use crate::day::Day;
use regex::{Captures, Regex};

pub struct Day01;

impl Day01 {
    fn convert_numbers(line: &String)-> String {
        line.replace("one", "1")
            .replace("two", "2")
            .replace("three", "3")
            .replace("four", "4")
            .replace("five", "5")
            .replace("six", "6")
            .replace("seven", "7")
            .replace("eight", "8")
            .replace("nine", "9")
    }

    fn parse_lines(input: &Vec<String>)-> Vec<i32> {
        let mut result = Vec::new();
        for line in input {
            let digits: Vec<&str> = line.matches(char::is_numeric).collect();
            result.push((digits[0].to_string() + digits[digits.len() - 1]).parse::<i32>().unwrap());
        }
        result
    }
}

impl Day for Day01 {
    fn solve_first(&self, input: &Vec<String>) -> String {
        // let mut num: i32 = 0;
        // for line in input {
        //     let digits: Vec<&str> = line.matches(char::is_numeric).collect();
        //     num += (digits[0].to_string() + digits[digits.len() - 1]).parse::<i32>().unwrap();
        //     println!("+ {:?}", digits[0].to_string() + digits[digits.len() - 1])
        // }
        Day01::parse_lines(input).iter().sum::<i32>().to_string()
    }
    fn solve_second(&self, input: &Vec<String>) -> String {
        // let first = Day01::parse_lines(&input);
        let re = Regex::new("(1|2|3|4|5|6|7|8|9|one|two|three|four|five|six|seven|eight|nine)").unwrap();
        let last = Regex::new("(.*)(1|2|3|4|5|6|7|8|9|one|two|three|four|five|six|seven|eight|nine)").unwrap();
        let regexed: Vec<String> = input.iter()
            .map(|line| re.replace(line, |c: &Captures| Day01::convert_numbers(&c[1].to_string())).to_string().to_string())
            .map(|line| last.replace(&line, |c: &Captures| format!("{}{}", &c[1].to_string(), (Day01::convert_numbers(&c[2].to_string())))).to_string())
            .collect();
        let converted = regexed.iter().map(Day01::convert_numbers).collect();
        // let second = Day01::parse_lines(&converted);
        // println!("{:?}", converted);
        // for i in 0..first.len() {
        //     println!("{}. {} - {} - {}", i+1, first[i], second[i], second[i] - first[i]);
        // }
        // println!("{}", second.iter().sum::<i32>());
        self.solve_first(&converted)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::vec_of_strings;

    #[test]
    fn test_example1() {
        let input = vec_of_strings!(
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet"
        );
        assert_eq!("142", (Day01 {}).solve_first(&input));
    }

    #[test]
    fn test_example2() {
        let input = vec_of_strings!(
            "two1nine",
            "eightwo1three",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen",
            "1",
            "oneight1"
        );
        assert_eq!("303", (Day01 {}).solve_second(&input));
    }

    #[test]
    fn test_example3() {
        let re = Regex::new("(1|2|3|4|5|6|7|8|9|one|two|three|four|five|six|seven|eight|nine)").unwrap();
        assert_eq!("9eightwo", re.replace("9eightwo", "${1}"));
        assert_eq!("8wo9", re.replace("eightwo9", |c: &Captures| Day01::convert_numbers(&c[1].to_string())).to_string());

        let last = Regex::new("(.*)(1|2|3|4|5|6|7|8|9|one|two|three|four|five|six|seven|eight|nine)(.*)").unwrap();
        assert_eq!("9eigh2", last.replace("9eightwoa", |c: &Captures| format!("{}{}", &c[1].to_string(), (Day01::convert_numbers(&c[2].to_string())))).to_string());

        let input = vec_of_strings!("9eightwo");
        assert_eq!("99", (Day01 {}).solve_first(&input));
        assert_eq!("92", (Day01 {}).solve_second(&input));
    }
}