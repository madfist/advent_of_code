#include <iostream>
#include <vector>

static const int W = 300;
static const int H = 300;

struct coord {
    int x, y;
};

std::ostream& operator<<(std::ostream& os, const coord& c) {
    os << c.x << ',' << c.y;
    return os;
}

struct coord2 {
    int x, y, s;
};

std::ostream& operator<<(std::ostream& os, const coord2& c) {
    os <<  c.x << ',' << c.y << ',' << c.s;
    return os;
}

void print_grid(const std::vector<int8_t>& grid) {
    for (int y = 0; y < H; ++y) {
        for (int x = 0; x < W; ++x) {
            std::cout << (int)grid[W*y+x] << ' ';
        }
        std::cout << '\n';
    }
}

void compute_grid(std::vector<int8_t>& grid, int grid_sn) {
    for (int x = 0; x < W; ++x) {
        for (int y = 0; y < H; ++y) {
            grid[W*y+x] = ((x+10)*((x+10)*y+grid_sn) % 1000) / 100 - 5;
        }
    }
}

coord broot(int grid_sn) {
    std::vector<int8_t> grid(W*H);
    compute_grid(grid, grid_sn);
    // print_grid(grid);
    int8_t max_power = 0;
    coord max_coord;
    for (int x = 0; x < W-2; ++x) {
        for (int y = 0; y < H-2; ++y) {
            int8_t sum = 0;
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    sum += grid[W*(y+j)+x+i];
                }
            }
            if (sum > max_power) {
                max_power = sum;
                max_coord = {x, y};
            }
        }
    }
    return max_coord;
}

coord2 broot2(int grid_sn) {
    std::vector<int8_t> grid(W*H);
    compute_grid(grid, grid_sn);
    int8_t max_power = 0;
    coord2 max_coord;
    for (int s = 1; s <= 300; ++s) {
        for (int x = 0; x < W-s+1; ++x) {
            for (int y = 0; y < H-s+1; ++y) {
                int8_t sum = 0;
                for (int i = 0; i < s; ++i) {
                    for (int j = 0; j < s; ++j) {
                        sum += grid[W*(y+j)+x+i];
                    }
                }
                if (sum > max_power) {
                    max_power = sum;
                    max_coord = {x, y, s};
                }
            }
        }
    }
    return max_coord;
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        std::cout << "Usage: " << argv[0] << " <grid serial number>" << std::endl;
        return 1;
    }
    int grid_sn = std::atoi(argv[1]);
    std::cout << "part1: " << broot(grid_sn) << std::endl;
    std::cout << "part2: " << broot2(grid_sn) << std::endl;
    return 0;
}