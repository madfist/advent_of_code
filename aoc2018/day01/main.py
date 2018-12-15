import sys

def para1(data):
    nums = data.split('\n')
    sum = 0
    for i in range(len(nums)):
        # print(i, nums[i])
        if len(nums[i]) > 0:
            sum += int(nums[i])
    print('part1', sum)

def para2(data):
    nums = data.split('\n')
    freqs = []
    sum = 0
    round = 0
    while 1:
        # print('round', round, sum)
        for diff in nums:
            # print(diff)
            if len(diff) > 0:
                sum += int(diff)
                if sum in freqs:
                    print('part2', sum)
                    return
                if round == 0:
                    freqs.append(sum)
        round += 1

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()

    para1(data)
    para2(data)

if __name__ == '__main__':
    main()
