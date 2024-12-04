use crate::day::Day;
use regex::Regex;

pub struct Day01;

impl Day01 {
    fn convert_numbers(line: &String)-> String {
        line.replace("_one", "1")
            .replace("_two", "2")
            .replace("_three", "3")
            .replace("_four", "4")
            .replace("_five", "5")
            .replace("_six", "6")
            .replace("_seven", "7")
            .replace("_eight", "8")
            .replace("_nine", "9")
    }
}

impl Day for Day01 {
    fn solve_first(&self, input: &Vec<String>) -> String {
        let mut num: i32 = 0;
        for line in input {
            let digits: Vec<&str> = line.matches(char::is_numeric).collect();
            num += (digits[0].to_string() + digits[digits.len() - 1]).parse::<i32>().unwrap();
            // println!("+ {:?}", digits[0].to_string() + digits[digits.len() - 1])
        }
        num.to_string()
    }
    fn solve_second(&self, input: &Vec<String>) -> String {
        let re = Regex::new("((one|two|three|four|five|six|seven|eight|nine))").unwrap();
        let regexed: Vec<String> = input.iter().map(|line| re.replace_all(line, "_$1").to_string()).collect();
        let converted = regexed.iter().map(Day01::convert_numbers).collect();
        // println!("{:?}", converted);
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
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen",
            "1",
            "oneight"
        );
        assert_eq!("303", (Day01 {}).solve_second(&input));
    }
}