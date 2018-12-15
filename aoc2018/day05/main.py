import sys

def check_pair(a, b):
    return (a.lower() == b.lower()) and (a.islower() and b.isupper() or a.isupper() and b.islower())

def part1(data):
    polymer = [data[0]]
    for unit in data[1:]:
        if len(polymer) > 0:
            if check_pair(polymer[-1], unit):
                # print('crash', polymer[-1], unit)
                polymer = polymer[:-1]
                continue
        polymer.append(unit)

    return len(polymer)


def part2(data):
    min_len = len(data)
    for unit in [chr(c) for c in range(ord('a'), ord('z')+1)]:
        filtered = [u for u in data if u.lower() != unit]
        # print(unit, len(filtered), part1(filtered))
        min_len = min(min_len, part1(filtered))
    return min_len

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()

    print('part1', part1(data))
    print('part2', part2(data))

if __name__ == '__main__':
    main()
