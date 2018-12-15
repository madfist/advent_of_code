#include <iostream>
#include <set>
#include <list>

class State {
public:
    State(int a, int b) : x(a), y(b) {}

    bool operator==(const State& that) const {
        return x == that.x && y == that.y;
    }

    bool operator!=(const State& that) const {
        return !(*this == that);
    }

    bool operator<(const State& that) const {
        return 256*y+x < 256*that.y+that.x;
    }

    int solve(const State& success) const {
        std::set<State> taboo;
        std::list<State> states;
        states.push_back(*this);
        State prev(0, 0);
        int i=0, pos=0, next=0, level=0;
        while (!states.empty() && states.front() != success) {
            std::list<State> chs = states.front().choices(taboo, prev);
            next += chs.size();
            states.splice(states.end(), chs);
            if (i++ == pos) {
                prev = states.front();
                pos += next;
                next = 0;
                ++level;
            }
            taboo.insert(states.front());
            states.pop_front();
        }
        return level;
    }

    int solve2(int max_level) const {
        std::set<State> taboo;
        std::list<State> states;
        states.push_back(*this);
        State prev(0, 0);
        int i=0, pos=0, next=0, level=0;
        std::set<State> full_path;
        while (!states.empty() && level < max_level) {
            std::list<State> chs = states.front().choices(taboo, prev);
            next += chs.size();
            states.splice(states.end(), chs);
            if (i++ == pos) {
                prev = states.front();
                pos += next;
                next = 0;
                ++level;
            }
            taboo.insert(states.front());
            full_path.insert(states.begin(), states.end());
            states.pop_front();
        }
        // std::set<State> ends(states.begin(), states.end());
        // print_maze(30, 30, full_path, ends);
        return full_path.size();
    }

    friend std::ostream& operator<<(std::ostream& stream, const State& state) {
        stream << "(" << state.x << "," << state.y << ")";
        return stream;
    }

    friend std::ostream& operator<<(std::ostream& stream, const std::list<State>& states) {
        stream << "[";
        for (std::list<State>::const_iterator it = states.begin(); it != states.end(); it++) {
            stream << *it << ",";
        }
        if (!states.empty()) {
            stream << "\b \b";
        }
        stream << "]";
        return stream;
    }

    friend void print_maze(int max_x, int max_y, const std::set<State>& states, const std::set<State>& ends) {
        for (int j=0; j<max_y; ++j) {
            for (int i=0; i<max_x; ++i) {
                State s(i, j);
                if (s.is_open()) {
                    if (ends.find(s) != ends.end()) {
                        std::cout << "X";
                    } else if (states.find(s) != states.end()) {
                        std::cout << "o";
                    } else {
                        std::cout << " ";
                    }
                } else {
                    std::cout << "█";
                }
            }
            std::cout << std::endl;
        }
        for (int i=0; i<max_x; ++i) {
            std::cout << "-";
        }
        std::cout << std::endl;
    }

    static int input;
    static State limit;
private:
    State up() const { return State(x, y-1); }
    State down() const { return State(x, y+1); }
    State left() const { return State(x-1, y); }
    State right() const { return State(x+1, y); }

    std::list<State> choices(const std::set<State>& taboo, const State& prev) const {
        std::list<State> chs;
        State try_up = up();
        State try_down = down();
        State try_left = left();
        State try_right = right();
        if (try_up != prev && taboo.find(try_up) == taboo.end() && !try_up.is_limit() && try_up.is_open()) { chs.push_back(try_up); }
        if (try_down != prev && taboo.find(try_down) == taboo.end() && !try_down.is_limit() && try_down.is_open()) { chs.push_back(try_down); }
        if (try_left != prev && taboo.find(try_left) == taboo.end() && !try_left.is_limit() && try_left.is_open()) { chs.push_back(try_left); }
        if (try_right != prev && taboo.find(try_right) == taboo.end() && !try_right.is_limit() && try_right.is_open()) { chs.push_back(try_right); }
        return chs;
    }

    bool is_limit() const {
        return x > limit.x || y > limit.y || x < 0 || y < 0;
    }

    bool is_open() const {
        return is_open(x, y);
    }

    static bool is_open(int i, int j) {
        int num = i*i + 3*i + 2*i*j + j + j*j + input;
        int one_count = 0;
        for (int k=0; num != 0; ++k) {
            one_count += num & 1;
            num >>= 1;
        }
        return (one_count%2 == 0);
    }

    int x,y;
};

int State::input = 0;
State State::limit = State(40,40);

int main(int argc, char* argv[]) {
    if (argc < 3) {
        std::cout << "Solution 1 usage: " << argv[0] << " <number> <end x> <end y>" << std::endl;
        std::cout << "Solution 2 usage: " << argv[0] << " <number> <max_steps>" << std::endl;
        return 1;
    }
    State::input = std::stoi(argv[1]);
    State start(1,1);
    if (argc == 3) {
        std::cout << "Locations: " << start.solve2(std::stoi(argv[2])) << std::endl;
    }
    if (argc == 4) {
        State end(std::stoi(argv[2]), std::stoi(argv[3]));
        std::cout << "Steps: " << start.solve(end) << std::endl;
    }
    return 0;
}