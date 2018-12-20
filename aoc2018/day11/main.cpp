#include <iostream>
#include <vector>

static const int W = 300;
static const int H = 300;

class Grid {
public:
    struct Coord {
        int x, y, s;
    };

    Grid(int grid_sn) : horizontal_grid(W*H), vertical_grid(H*W) {
        for (int x = 0; x < W; ++x) {
            for (int y = 0; y < H; ++y) {
                horizontal_grid[W*y+x] = ((x+10)*((x+10)*y+grid_sn) % 1000) / 100 - 5;
                vertical_grid[H*x+y] = ((x+10)*((x+10)*y+grid_sn) % 1000) / 100 - 5;
            }
        }
    }

    void print() {
        for (int y = 0; y < H; ++y) {
            for (int x = 0; x < W; ++x) {
                std::cout << horizontal_grid[W*y+x] << ' ';
            }
            std::cout << '\n';
        }
    }

    Coord get_max_power_coord(int grid_size) {
        int max_power = 0;
        Coord max_coord;
        for (int x = 0; x < W-grid_size+1; ++x) {
            for (int y = 0; y < H-grid_size+1; ++y) {
                int sum = sum_sub_grid({x, y, grid_size});
                if (sum > max_power) {
                    max_power = sum;
                    max_coord = {x, y, grid_size};
                }
            }
        }
        return max_coord;
    }

    Coord get_max_power_coord() {
        int max_power = 0;
        Coord max_coord;
        std::vector<int> prev_sum(horizontal_grid);
        std::vector<int> grid_sum;
        for (int s = 2; s <= W; ++s) {
            grid_sum.clear();
            grid_sum.resize((W-s+1)*(H-s+1));
            for (int x = 0; x < W-s+1; ++x) {
                for (int y = 0; y < H-s+1; ++y) {
                    int sum = prev_sum[(W-s+2)*y+x] + sum_right_lower_curb({x, y, s});
                    grid_sum[(W-s+1)*y+x] = sum;
                    if (sum > max_power) {
                        max_power = sum;
                        max_coord = {x, y, s};
                    }
                }
            }
            prev_sum = grid_sum;
        }
        return max_coord;
    }
private:
    std::vector<int> horizontal_grid;
    std::vector<int> vertical_grid;

    int sum_sub_grid(Coord coord) {
        int sum = 0;
        for (int i = 0; i < coord.s; ++i) {
            for (int j = 0; j < coord.s; ++j) {
                sum += horizontal_grid[W*(coord.y+j)+coord.x+i];
            }
        }
        return sum;
    }

    int sum_right_lower_curb(Coord coord) {
        int sum = 0;
        for (int i = 0; i < coord.s; ++i) {
            sum += horizontal_grid[W*(coord.y+coord.s-1)+coord.x+i];
        }
        for (int i = 0; i < coord.s-1; ++i) {
            sum += vertical_grid[H*(coord.x+coord.s-1)+coord.y+i];
        }
        return sum;
    }
};

std::ostream& operator<<(std::ostream& os, const Grid::Coord& c) {
    os <<  c.x << ',' << c.y << ',' << c.s;
    return os;
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        std::cout << "Usage: " << argv[0] << " <grid serial number>" << std::endl;
        return 1;
    }
    int grid_sn = std::atoi(argv[1]);
    Grid grid(grid_sn);
    std::cout << "part1: " << grid.get_max_power_coord(3) << std::endl;
    std::cout << "part2: " << grid.get_max_power_coord() << std::endl;
    return 0;
}