#include <iostream>
#include <algorithm>
#include <vector>
#include <deque>

// inspired by reddit and python deque
class Circle : public std::deque<int> {
public:
    Circle() : std::deque<int>(1, 0) {}
    void rotate(int step) {
        if (step < 0) {
            for (int i = step; i < 0; ++i) {
                push_back(front());
                pop_front();
            }
        } else {
            for (int i = 0; i < step; ++i) {
                push_front(back());
                pop_back();
            }
        }
    }
};

long get_winner_score(int players, int marbles) {
    Circle circle;
    std::vector<long> points(players, 0);
    int current_player = 0;
    for (int m = 1; m <=marbles; ++m) {
        if (m % 23 == 0) {
            circle.rotate(7);
            points[current_player++ % players] += m + circle.back();
            circle.pop_back();
            circle.rotate(-1);
        } else {
            circle.rotate(-1);
            circle.push_back(m);
        }
    }
    return *std::max_element(points.begin(), points.end());
}

int main(int argc, char* argv[]) {
    if (argc < 3) {
        std::cout << "Usage: " << argv[0] << " <players> <marbles>" << std::endl;
        return 1;
    }
    int players = std::atoi(argv[1]);
    int marbles = std::atoi(argv[2]);
    std::cout << "part1: " << get_winner_score(players, marbles) << "\npart2: " << get_winner_score(players, marbles*100) << std::endl;
    return 0;
}