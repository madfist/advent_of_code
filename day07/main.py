import sys
import re

def process_input(data):
    lines = data.split('\n')
    edges = []
    message = []
    for line in lines:
        m = re.match(r'Step\ (\D+)\ must\ be\ finished\ before\ step\ (\D+)\ can\ begin', line)
        edges.append((m.group(1), m.group(2)))
    edges.sort(key=lambda t: ord(t[0]) * (ord('Z')-ord('A')) + ord(t[1]))
    graph = {}
    parents = {}
    # graph['r'] = [chr(o) for o in range(ord('A'), ord('Z')+1)]
    graph['root'] = []
    for e in edges:
        if e[0] in graph:
            graph[e[0]].append(e[1])
            graph[e[0]].sort()
        else:
            graph[e[0]] = [e[1]]
        if e[1] in parents:
            parents[e[1]] += 1
        else:
            parents[e[1]] = 1
    for o in range(ord('A'), ord('Z')+1):
        if chr(o) not in graph:
            graph[chr(o)] = []
            continue
        if chr(o) not in parents:
            parents[chr(o)] = 0
        is_in = False
        for k,v in graph.items():
            if chr(o) in v:
                is_in = True
                break
        if not is_in:
            graph['root'].append(chr(o))
    # for k,v in graph.items():
    #     print(k, v)
    return graph, parents

def part1(data):
    graph, parents = process_input(data)
    reserve = graph['root']
    answer = ''
    while reserve:
        x = reserve[0]
        reserve = reserve[1:]
        answer += x
        for y in graph[x]:
            parents[y] -=1
            if parents[y] == 0:
                reserve.append(y)
                reserve.sort()
    return answer

def part2(data):
    graph, parents = process_input(data)

    task_time = lambda x: 61 + ord(x) - ord('A')

    tasks = []
    queue = graph['root']
    time = 0

    def schedule():
        nonlocal tasks
        nonlocal queue
        while len(tasks) < 5 and queue:
            x = queue[0]
            queue = queue[1:]
            tasks.append((x, time + task_time(x)))
        tasks.sort(key=lambda tup: tup[1])

    schedule()
    while queue or tasks:
        x,time = tasks[0]
        tasks = tasks[1:]
        for y in graph[x]:
            parents[y] -= 1
            if parents[y] == 0:
                queue.append(y)
                queue.sort()
        schedule()

    return time

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
