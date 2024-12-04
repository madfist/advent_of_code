use std::fs::read_to_string;
use std::env::args;
use std::process::exit;

mod utils;
mod day;
mod day01;

fn main() {
    if args().len() < 2 {
        println!("Usage: {:?} <day no.>", args().nth(0).expect("aoc2023"));
        exit(1);
    }
    let day_no_str = args().nth(1).expect("1");
    let filename = format!("src/resources/day{:0>2}.input", day_no_str);
    let input = read_lines(&filename);
    let day_no: u8 = day_no_str.parse().unwrap();
    let day = get_day(day_no);

    println!("day {:?}:\n{:?}\n{:?}", day_no, day.solve_first(&input), day.solve_second(&input));
}

fn get_day(num: u8) -> Box<dyn day::Day> {
    match num {
        1 => Box::new(day01::Day01 {}),
        _ => {
            println!("Day {:?} not implemented yet", num);
            exit(1);
        }
    }
}

fn read_lines(filename: &str) -> Vec<String> {
    let mut result = Vec::new();

    for line in read_to_string(filename).unwrap().lines() {
        result.push(line.to_string())
    }

    result
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_file_read() {
        let result = read_lines("src/resources/read_test.txt");
        assert_eq!(result, ["read", "test"]);
    }
}
