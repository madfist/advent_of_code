import sys

class Recipe:
    def __init__(self, initlist):
        self.recipes = initlist
        self.elf1 = 0
        self.elf2 = 1
        self.__rotate = lambda r,p,s: (r+p)%s
    def add_new(self):
        new_recipe = self.recipes[self.elf1] + self.recipes[self.elf2]
        if new_recipe > 9:
            self.recipes.append(new_recipe // 10)
            self.recipes.append(new_recipe % 10)
        else:
            self.recipes.append(new_recipe)
        self.elf1 = self.__rotate(1 + self.recipes[self.elf1], self.elf1, len(self.recipes))
        self.elf2 = self.__rotate(1 + self.recipes[self.elf2], self.elf2, len(self.recipes))

def part1(data):
    r = Recipe([3, 7])
    while len(r.recipes) < data + 10:
        r.add_new()
        # print(r.recipes)

    return ''.join(str(x) for x in r.recipes[data:data+10])


def part2(data):
    r = Recipe([3, 7])
    data_list = [int(x) for x in str(data)]
    print(data_list, r.recipes[-len(data_list):])
    while r.recipes[-len(data_list):] != data_list and r.recipes[-len(data_list)-1:-1] != data_list:
        r.add_new()
        # print(r.recipes)

    if r.recipes[-len(data_list)] == data_list[0]:
        return len(r.recipes) - len(data_list)
    else:
        return len(r.recipes) - len(data_list) - 1

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    # with open(sys.argv[1], 'r') as input:
    #     data = input.read()
    data = int(sys.argv[1])

    print('part1', part1(data))
    print('part2', part2(data))

if __name__ == '__main__':
    main()
