import sys
import difflib

def part1(data):
    lines = data.split('\n')
    doubles = 0
    triples = 0

    for line in lines:
        if len(line) < 1:
            continue
        dict = {}
        for letter in line:
            if letter in dict:
                dict[letter] += 1
            else:
                dict[letter] = 1
        for k,v in dict.items():
            # print(k, v, end='')
            if v == 2:
                doubles += 1
                break
        for k,v in dict.items():
            if v == 3:
                triples += 1
                break

    return doubles * triples

def part2(data):
    lines = data.split('\n')
    d = difflib.Differ()

    for i in range(len(lines)):
        for j in range(i+1, len(lines)):
            # print(i, j, k, len(lines[i]), len(lines[j]))
            if len(lines[i]) < 1 or len(lines[j]) < 1:
                continue
            diff = list(d.compare([lines[i]], [lines[j]]))
            if diff[1].count('^') == 1 and diff[1].count('-') == 0:
                # print(diff[0])
                # print(diff[1])
                pos = diff[1].find('^') - 2
                return lines[i][:pos] + lines[i][pos+1:]

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
