import sys

def sum_metadata(idx, nums):
    sum = 0
    if (nums[idx] > 0):
        for i in range(nums[idx]):
            s, idx = sum_metadata(idx+2, nums)
            sum += s
    else:
        for i in range(nums[idx+1]):
            sum += nums[idx+2+i]
        idx += 2 + nums[idx+1]
    return sum, idx

def part1(data):
    nums = [int(d) for d in data.split(' ')]
    sum = 0
    while len(nums) > 0:
        for i in range(0, len(nums), 2):
            if nums[i] == 0:
                for j in range(nums[i+1]):
                    sum += nums[i+2+j]
                nums = nums[:i] + nums[i+2+nums[i+1]:]
                if i > 1:
                    nums[i-2] -= 1
                break
    return sum

def check_part(part):
    print('check', part, end='')
    ret = True
    for i in range(part[0]):
        ret = ret and (part[i+2] < 1)
    print(ret)
    return ret

def sum_part(part):
    sum = 0
    start = 2 + part[0]
    for i in range(start, start + part[1]):
        if part[i] <= part[1]:
            sum += part[part[i] + 1]
    print('sum', sum)
    return sum

def part2(data):
    nums = [int(d) for d in data.split(' ')]
    sum = 0
    i = 0
    # print('before', len(nums), nums[:150])
    while i < len(nums):
        if nums[i] == 0:
            s = 0
            for j in range(nums[i+1]):
                s += nums[i+2+j]
            print('null ->', i, nums[i-2:i+nums[i+1]+nums[i-1]])
            nums = nums[:i] + [-s] + nums[i+2+nums[i+1]:]
            print('null <-', i, nums[i-2:i+nums[i+1]+nums[i-1]])
            if nums[i-2] == 1:
                i += nums[i-1]
            i += 1
        else:
            i += 2

    i = 0
    while i < len(nums):
        if nums[i] == 1 and nums[i+2] < 0:
            s = sum_part(nums[i:i+3+nums[i+1]])
            print('one ->', i, nums[i-2:i+nums[i+1]+nums[i-1]])
            nums = nums[:i] + [s] + nums[i+3+nums[i+1]:]
            print('one <-', i, nums[i-2:i+nums[i+1]+nums[i-1]])
            if nums[i-2] == 1:
                i += nums[i-1]
            i += 1
        else:
            i += 2
    # print('middle', len(nums), nums[:150])
    return
    while len(nums) > 1:
    # for k in range(3):
        # print('in', len(nums), nums)
        for i in range(len(nums)):
            # print('i', i, nums[i-2:i+1], nums[:150])
            if nums[i] < 0 and nums[i-2] > 0 and check_part(nums[i-2:i+nums[i-2]]):
                print('i', i, nums[i-2:i+1], nums[:i+200])
                s = sum_part(nums[i-2:i+nums[i-2]+nums[i-1]])
                nums = nums[:i-2] + [s] + nums[i+nums[i-2]+nums[i-1]:]
                break
            elif nums[i] < 0 and nums[i-2] > 0:
                print('i', i, nums[i-2:i+1], nums[:i+200])
                pass

    return abs(nums[0])

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
