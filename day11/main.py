import sys
import re

# # example1 = [['HM', 'LM'], ['HG'], ['LG'], []]
# example1 = [{'m':['H', 'L'], 'g':[]}, {'m':[], 'g':['H']}, {'m':[], 'g':['L']}, {'m':[], 'g':[]}]
# # input_data = [['PrM', 'PrG'], ['CoG', 'CuG', 'RuG', 'PuG'], ['CoM', 'CuM', 'RuM', 'PuM'], []]
# input_data = [{'m':['A'], 'g':['A']}, {'m':[], 'g':['B', 'C', 'D', 'E']}, {'m':['B', 'C', 'D', 'E'], 'g':[]}, {'m':[], 'g':[]}]

# def check_floor(plan, floor):
#     m_ok = 0
#     for m in plan[floor]['m']:
#         if m in plan[floor]['g']:
#             m_ok += 1
#     if len(plan[floor]['m']) > m_ok and len(plan[floor]['g']) > 0:
#         return False
#     return True

# def check_floors(plan):
#     for f in range(len(plan)):
#         if not check_floor(plan, f):
#             print("floor failed", f, plan[f])
#             return False
#     return True

# def go_up(plan, floor, stuff):
#     if floor > 2:
#         return False
#     print("UP", floor, plan[floor], plan[floor+1], "|", stuff)
#     for m in stuff['m']:
#         plan[floor]['m'].remove(m)
#     plan[floor+1]['m'].extend(stuff['m'])
#     for g in stuff['g']:
#         plan[floor]['g'].remove(g)
#     plan[floor+1]['g'].extend(stuff['g'])
#     print("AFTER UP", plan[floor], plan[floor+1])
#     return True

# def go_down(plan, floor, stuff):
#     if floor < 1:
#         return False
#     print("DOWN", floor, plan[floor], plan[floor-1], "|", stuff)
#     for m in stuff['m']:
#         plan[floor]['m'].remove(m)
#     plan[floor-1]['m'].extend(stuff['m'])
#     for g in stuff['g']:
#         plan[floor]['g'].remove(g)
#     plan[floor-1]['g'].extend(stuff['g'])
#     print("AFTER DOWN", plan[floor], plan[floor-1])
#     return True

# def choose_package(plan, floor):
#     options = []
#     for m in plan[floor]['m']:
#         options.append({'m': [m], 'g':[]})
#         for m2 in plan[floor]['m']:
#             if m is not m2:
#                 options.append({'m': [m, m2], 'g':[]})
#     for g in plan[floor]['g']:
#         options.append({'m': [], 'g': [g]})
#         for g2 in plan[floor]['g']:
#             if g is not g2:
#                 options.append({'m': [], 'g': [g, g2]})
#     for m in plan[floor]['m']:
#         for g in plan[floor]['g']:
#             options.append({'m': [m], 'g':[g]})
#     return options

# def is_success(plan):
#     for f in range(len(plan)-1):
#         if len(plan[f]['m']) > 0 or len(plan[f]['g']) > 0:
#             return False
#     return True

# def solve(plan, floor, step=0):
#     pkgs = choose_package(plan, floor)
#     print("step", step, "pkgs", pkgs)
#     s = [0,0]
#     ret = []
#     for p in pkgs:
#         print("P", p)
#         to_do = False
#         if go_up(plan, floor, p):
#             if check_floors(plan):
#                 print("go_up")
#                 s[1] = solve(plan, floor+1, step+1)
#                 to_do = True
#             go_down(plan, floor+1, p)
#         if go_down(plan, floor, p):
#             if check_floors(plan):
#                 print("go_down")
#                 s[0] = solve(plan, floor-1, step+1)
#                 to_do = True
#             go_up(plan, floor-1, p)
#         if not to_do:
#             if is_success(plan):
#                 ret.append(step)
#             else:
#                 ret.append(sys.maxsize)
#         else:
#             ret.append(min(s))
#     return min(ret)

example1 = [[0,1],[0,2]]
input_data = [[0,0],[2,1],[2,1],[2,1],[2,1]]
taboo = []

def check_floor(plan, floor):
    m_on = 0
    g_on = 0
    m_ok = 0
    for item in plan:
        if item[0] == floor:
            m_on += 1
            if item[1] == floor:
                m_ok += 1
        if item[1] == floor:
            g_on += 1
    if m_on > m_ok and g_on > m_on:
        return False
    return True

def check_floors(plan):
    for f in range(4):
        if not check_floor(plan, f):
            print("____FAILED:", f)
            return False
    return True

def hash_plan(plan, floor):
    hash = 0
    for i in range(len(plan)):
        hash += (plan[i][0]*4 + plan[i][1]) << i*4
    return (hash << 2) + floor

def is_success(plan):
    for item in plan:
        if item[0] is not 3 or item[1] is not 3:
            return False
    return True

def choose_package(plan, floor):
    singles = []
    doubles = []
    for i in range(len(plan)):
        for j in range(2):
            if plan[i][j] == floor:
                singles.append([i, j])
    for s in range(len(singles)-1):
        for t in range(s+1, len(singles)):
            doubles.append([singles[s], singles[t]])
    singles.extend(doubles)
    return singles

def move(plan, stuff, direction):
    if isinstance(stuff[0], list):
        for s in stuff:
            plan[s[0]][s[1]] += direction
    else:
        plan[stuff[0]][stuff[1]] += direction

def solve_bfs(plan):
    pkgs = choose_package(plan, floor)

def solve(plan, floor, step=0, prev_hash=[]):
    this_hash = hash_plan(plan, floor)
    prev_hash.append(this_hash)
    print("======> F:", floor, "S:", step, "P:", plan, "H:", this_hash, "prev:", prev_hash)
    if is_success(plan):
        print("solution found")
        prev_hash.remove(this_hash)
        return step
    # if step > 11:
    #     print("XXXXXXXXXXXXXXXX")
    #     prev_hash.remove(this_hash)
    #     return sys.maxsize
    pkgs = choose_package(plan, floor)
    print("======> PKGS:", pkgs)
    s = [0,0]
    ret = []
    for p in pkgs:
        to_do = False
        if floor < 3:
            move(plan, p, 1)
            hu = hash_plan(plan, floor+1)
            print("try up s", step, p, hu)
            if not check_floors(plan):
                print("invalid floor", plan)
            if hu in taboo:
                print("taboo")
            if hu in prev_hash:
                print("no going back", prev_hash)
            if check_floors(plan) and not hu in prev_hash and not hu in taboo:
                print("go up")
                s[0] = solve(plan, floor+1, step+1, prev_hash)
                to_do = True
            move(plan, p, -1)
        if floor > 0:
            move(plan, p, -1)
            hd = hash_plan(plan, floor-1)
            print("try down s", step, p, hd)
            if not check_floors(plan):
                print("invalid floor", plan)
            if hd in taboo:
                print("taboo")
            if hd in prev_hash:
                print("no going back", prev_hash)
            if check_floors(plan) and not hd in prev_hash and not hd in taboo:
                print("go down")
                s[1] = solve(plan, floor-1, step+1, prev_hash)
                to_do = True
            move(plan, p, 1)
        if not to_do:
            print("deadend F:", floor, "S:", step, "P:", plan, "H:", this_hash)
            ret.append(sys.maxsize)
        else:
            print("backup F:", floor, "S:", step, "P:", plan, "H:", this_hash)
            ret.append(min(s))
    print("finish level", step, min(ret))
    prev_hash.remove(this_hash)
    taboo.append(this_hash)
    return min(ret)

def main():
    print("min steps:", solve(example1, 0))

if __name__ == '__main__':
    main()