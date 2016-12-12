import sys
import re

example1 = [['HM', 'LM'], ['HG'], ['LG'], []]
input_data = [['PrM', 'PrG'], ['CoG', 'CuG', 'RuG', 'PuG'], ['CoM', 'CuM', 'RuM', 'PuM'], []]

def check_floor(plan, floor):
    if

def go_up(plan, floor, stuff):
    if floor > 2:
        return False
    for s in stuff:
        plan[floor].remove(s)
    plan[floor+1].extend(stuff)
    return True

def go_down(plan, floor, stuff):
    if floor < 1:
        return False
    for s in stuff:
        plan[floor].remove(s)
    plan[floor-1].extend(stuff)
    return True

def main():
    # if (len(sys.argv) < 2):
    #     print("Usage python3 %s <input>" % sys.argv[0])
    #     exit(-1)
    # with open(sys.argv[1], 'r') as input:
    #     data = input.read()

if __name__ == '__main__':
    main()