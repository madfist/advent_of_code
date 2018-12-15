class Disc(object):
    def __init__(self, i, c, s):
        self.index = i
        self.cycle = c
        self.start = s

    def pos(self, t):
        return (self.start + self.index + t) % self.cycle

def check(circulars, t):
    for c in circulars:
        if c.pos(t) != 0:
            return False
    return True

def main():
    example1 = [Disc(1,5,4), Disc(2,2,1)]
    input_data = [Disc(1,13,10), Disc(2,17,15), Disc(3,19,17), Disc(4,7,1), Disc(5,5,0), Disc(6,3,1)]
    input_data2 = [Disc(1,13,10), Disc(2,17,15), Disc(3,19,17), Disc(4,7,1), Disc(5,5,0), Disc(6,3,1), Disc(7,11,0)]
    fallout1 = True
    fallout2 = True
    t = 18 # time when largest disc is at 0
    while fallout1 or fallout2:
        t += 19 # largest disc
        if fallout1 and check(input_data, t):
            print("Time1:", t)
            fallout1 = False
        if fallout2 and check(input_data2, t):
            print("Time2:", t)
            fallout2 = False

if __name__ == '__main__':
    main()