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

# zero-starting nodes are leaves, sum them,
# remove them from the list and decrease parents node count,
# repeat from beginning
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

# same as before, but when leaves are removed, convert node count
# into a list, decrease node count and append the sum of the leaf
# from then use the metadata on the inner list when node count
# drops to zero
def part2(data):
    nums = [int(d) for d in data.split(' ')]
    while len(nums) > 1:
        for i in range(0, len(nums), 2):
            # print('--- index', i)
            sum = 0
            if nums[i] == 0:
                for j in range(nums[i+1]):
                    sum += nums[i+2+j]
                # print('remove', nums[i:i+2+nums[i+1]], s)
                nums = nums[:i] + nums[i+2+nums[i+1]:]
                if i > 1:
                    # print('\tfrom', nums[i-2:i+10])
                    nums[i-2][0] -= 1
                    nums[i-2].append(sum)
                    # print('\tto', nums[i-2:i+10])
                break
            elif isinstance(nums[i], list) and nums[i][0] == 0:
                for j in range(nums[i+1]):
                    if nums[i+2+j] < len(nums[i]):
                        sum += nums[i][nums[i+2+j]]
                # print('x remove', nums[i:i+2+nums[i+1]], s)
                nums = nums[:i] + nums[i+2+nums[i+1]:]
                if i > 1:
                    # print('\tfrom', nums[i-2:i+10])
                    nums[i-2][0] -= 1
                    nums[i-2].append(sum)
                    # print('\tto', nums[i-2:i+10])
                break
            elif not isinstance(nums[i], list):
                nums[i] = [nums[i]]
    return sum

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
