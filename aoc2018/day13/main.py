import sys

class car:
    def __init__(self, direction, x, y, next_direction='l'):
        self.direction = direction
        self.x = x
        self.y = y
        self.next_direction = next_direction
        self.__directions = {'l': 's', 's': 'r', 'r': 'l'}
        self.__turner = {
            'l': {
                '^': '<',
                'v': '>',
                '<': 'v',
                '>': '^'
            },
            'r': {
                '^': '>',
                'v': '<',
                '<': '^',
                '>': 'v'
            },
            '/': {
                '^': '>',
                'v': '<',
                '<': 'v',
                '>': '^'
            },
            '\\': {
                '^': '<',
                'v': '>',
                '<': '^',
                '>': 'v'
            }
        }

    def __str__(self):
        return self.direction + '(' + str(self.x) + ',' + str(self.y) + ')' + self.next_direction

    def move(self):
        if self.direction == '^':
            self.y -= 1
        elif self.direction == 'v':
            self.y += 1
        elif self.direction == '<':
            self.x -= 1
        elif self.direction == '>':
            self.x += 1

    def turn(self, track=None):
        if track:
            self.direction = self.__turner[track][self.direction]
            return
        if self.next_direction != 's':
            self.direction = self.__turner[self.next_direction][self.direction]
        self.next_direction = self.__directions[self.next_direction]



class track_field:
    def __init__(self, lines):
        self.lines = lines
        self.size_x = len(lines[0])
        self.size_y = len(lines)
        self.cars = self.__get_cars(lines)
        self.__sorter = lambda car: car.y*self.size_x + car.x

    def __get_cars(self, lines):
        cars = []
        track_switcher = {
            '^': '|',
            'v': '|',
            '<': '-',
            '>': '-'
        }
        for j,line in enumerate(lines):
            new_line = ''
            for i,l in enumerate(line):
                if l == '^' or l == 'v' or l == '<' or l == '>':
                    cars.append(car(l, i, j))
                    new_line += track_switcher[l]
                else:
                    new_line += l
            self.lines[j] = new_line
        return cars

    def print_cars(self):
        for car in self.cars:
            print(car, '', end='')
        print()

    def print_field(self):
        plines = []
        for line in self.lines:
            plines.append(line)
        for car in self.cars:
            plines[car.y] = plines[car.y][:car.x] + car.direction + plines[car.y][car.x+1:]
        print('\n'.join(plines))

    def get_cell(self, x, y):
        return self.lines[y][x]

    def check_collision(self, i, car):
        for j, c in enumerate(self.cars):
            if i == j:
                continue
            if c.x == car.x and c.y == car.y:
                return j, c
        return None, None

    def tick(self, remove=False):
        to_delete = []
        self.cars.sort(key=self.__sorter)
        for i, car in enumerate(self.cars):
            car.move()
            j, c = self.check_collision(i, car)
            if c:
                if not remove:
                    return car.x, car.y
                to_delete.append(i)
                to_delete.append(j)
                continue
            cell = self.get_cell(car.x, car.y)
            if cell == '+':
                car.turn()
            if cell == '\\' or cell == '/':
                car.turn(cell)
        if to_delete:
            c = 0
            for i in sorted(to_delete):
                del self.cars[i-c]
                c += 1
        if len(self.cars) == 1:
            return self.cars[0].x, self.cars[0].y
        return None



def part1(data):
    lines = data.split('\n')
    tf = track_field(lines)
    res = tf.tick()
    while not res:
        res = tf.tick()
    return res

def part2(data):
    lines = data.split('\n')
    tf = track_field(lines)
    res = tf.tick(True)
    while not res:
        res = tf.tick(True)
    return res

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
