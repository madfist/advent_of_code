import sys
import re

def bounds(x_coords, y_coords):
    min_x = x_coords[0]
    max_x = x_coords[0]
    min_y = y_coords[0]
    max_y = y_coords[0]
    for i in range(len(x_coords)):
        min_x = min(min_x, x_coords[i])
        min_y = min(min_y, y_coords[i])
        max_x = max(max_x, x_coords[i])
        max_y = max(max_y, y_coords[i])
    return (min_x, min_y, max_x, max_y)

def distance(x1, y1, x2, y2):
    return abs(x1-x2) + abs(y1-y2)

def min_distance_from_bounds(x, y, b):
    return min(min(abs(b[0]-x), abs(b[2]-x)), min(abs(b[1]-y), abs(b[3]-y)))

def part12(data):
    lines = data.split('\n')
    x_coords = []
    y_coords = []
    for line in lines:
        m = re.match(r'(\d+),\ (\d+)', line)
        x_coords.append(int(m.group(1)))
        y_coords.append(int(m.group(2)))

    b = bounds(x_coords, y_coords)
    size_x = b[2] - b[0]
    size_y = b[3] - b[1]
    for i in range(len(x_coords)):
        x_coords[i] -= b[0]
        y_coords[i] -= b[1]
    grid = [-1] * size_x * size_y

    middle_size = 0

    # build the grid
    for i in range(size_x):
        for j in range(size_y):
            min_dist = size_x + size_y
            sum = 0
            for l in range(len(x_coords)):
                dist = distance(i, j, x_coords[l], y_coords[l])
                sum += dist
                if dist < min_dist:
                    min_dist = dist
                    grid[j*size_x + i] = l
                elif min_dist == dist:
                    grid[j*size_x + i] = -1
            if sum < 10000:
                middle_size += 1
    # print(grid)


    # cut the infinites
    infinites = set()
    for i in range(size_x):
        infinites.add(grid[i])
        infinites.add(grid[i+(size_y-1)*size_x])

    for i in range(size_y):
        infinites.add(grid[i*size_x])
        infinites.add(grid[size_x-1 + i*size_x])
    # print(infinites)

    # count areas
    areas = {}
    for i in range(size_x*size_y):
        if grid[i] not in infinites:
            if grid[i] not in areas:
                areas[grid[i]] = 0
            areas[grid[i]] += 1
    # print(areas)

    max_area = 0
    for v in areas.values():
        max_area = max(max_area, v)

    return max_area, middle_size

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
