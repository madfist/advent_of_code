#!/usr/bin/python3

import sys

if __name__ == "__main__":
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    lines = data.split("\n");
    sum1 = 0
    sum2 = 0
    for l in lines:
        numss = l.split("\t");
        nums = list(map(lambda x: int(x), numss))
        
        sum1 = sum1 + max(nums) - min(nums)
        
        for i in range(0,len(nums)-1):
            for j in range(i+1,len(nums)):
                if nums[i]%nums[j] == 0:
                    sum2 = sum2 + nums[i]//nums[j]
                    break;
                if nums[j]%nums[i] == 0:
                    sum2 = sum2 + nums[j]//nums[i]
                    break;
    print("Sum1:", sum1)
    print("Sum2:", sum2)