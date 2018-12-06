import sys
import re

def part12(data):
    lines = data.split('\n')
    timetable = {}
    guard = -1
    start = -1
    for line in sorted(lines):
        # print(line)
        guard_m = re.match(r'\[\d+-\d+-\d+\ \d+:\d+\]\ Guard #(\d+)\ begins\ shift', line)
        if guard_m:
            guard = int(guard_m.group(1))
            continue
        sleep_m = re.match(r'\[\d+-\d+-\d+\ \d+:(\d+)\]\ falls\ asleep', line)
        if sleep_m:
            if guard not in timetable:
                timetable[guard] = [0] * 60;
            start = int(sleep_m.group(1))
            continue
        wake_m = re.match(r'\[\d+-\d+-\d+\ \d+:(\d+)\]\ wakes\ up', line)
        if wake_m:
            end = int(wake_m.group(1))
            for i in range(start, end):
                timetable[guard][i] += 1

    max_sleep_time = 0
    max_sleep_time2 = 0
    max_sleep_guard = -1
    max_sleep_guard2 = -1
    max_sleep_minute = -1
    max_sleep_minute2 = -1
    for g,s in timetable.items():
        # print(g, ','.join(str(sm) for sm in s))
        sum = 0
        mx = 0
        mx2 = 0
        idx = -1
        idx2 = -1
        for i,m in enumerate(s):
            sum += m
            if m > mx:
                mx = m
                mx2 = m
                idx = i
                idx2 = i
        if mx2 > max_sleep_time2:
            max_sleep_time2 = mx2
            max_sleep_guard2 = g
            max_sleep_minute2 = idx2
        if sum > max_sleep_time:
            max_sleep_time = sum
            max_sleep_guard = g
            max_sleep_minute = idx

    # print(max_sleep_guard, max_sleep_time, max_sleep_minute)
    # print(max_sleep_guard2, max_sleep_time2, max_sleep_minute2)
    return (max_sleep_guard * max_sleep_minute, max_sleep_guard2 * max_sleep_minute2)

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()

    part1, part2 = part12(data)
    print('part1', part1)
    print('part2', part2)

if __name__ == '__main__':
    main()
