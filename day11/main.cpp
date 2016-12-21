#include <iostream>
#include <list>
#include <set>
#include <string>
#include <sstream>

class State {
public:
    State(int nn, int ss): n(nn), s(ss) {}

    int solve(const State& success_state) const {
        std::set<State> taboo;
        std::list<State> states;
        states.push_back(*this);
        std::list<State>::iterator it = states.begin();
        int i=0, pos=0, next=0, level=0;
        while (it != states.end() && (*it) != success_state) {
            std::list<State> chs = it->choices(taboo);
            next += chs.size();
            states.splice(states.end(), chs);
            if (i++ == pos) {
                level++;
                pos += next;
                next = 0;
            }
            taboo.insert(*it);
            states.pop_front();
            it = states.begin();
            // return -1;
        }
        return level;
    }

    bool operator==(const State& that) const {
        return this->s == that.s && this->n == that.n;
    }

    bool operator!=(const State& that) const {
        return this->s != that.s || this->n != that.n;
    }

    bool operator<(const State& that) const {
        return this->s < that.s;
    }

    friend std::ostream& operator<<(std::ostream& stream, const State& state) {
        stream << "(" << state.get_floor(state.n-1) << "|";
        for (int i=state.n-2; i>=0; --i) {
            stream << state.get_floor(i);
            if (i > 0) {
                stream << ",";
            }
        }
        stream << ")";
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

private:
    int get_floor(int i) const {
        return (s & (3<<2*i))>>2*i;
    }

    State up(int i) const {
        return State(n, s + (1<<2*i));
    }

    State down(int i) const {
        return State(n, s - (1<<2*i));
    }

    bool check() const {
        for (int f=0; f<4; ++f) {
            int m_ok = 0, m_on = 0, g_on = 0;
            for (int i = 0; i < n-1; i+=2) {
                if (get_floor(i) == f) {
                    m_on++;
                    if (get_floor(i+1) == f) {
                        m_ok++;
                    }
                }
                if (get_floor(i+1) == f) {
                    g_on++;
                }
            }
            if (m_on > 0 && m_on > m_ok && g_on > 0) {
                return false;
            }
        }
        return true;
    }

    std::list<State> choices(const std::set<State>& taboo) const {
        std::list<State> chs;
        if (taboo.find(*this) != taboo.end()) {
            return chs;
        }
        for (int i=0; i<n-1; ++i) {
            int floor = get_floor(n-1);
            int item = get_floor(i);
            if (item == floor && floor < 3 && item < 3) {
                State try_up = up(n-1).up(i);
                if (try_up.check()) {
                    chs.push_back(try_up);
                }
                for (int j=i+1; j<n-1; ++j) {
                    int item2 = get_floor(j);
                    State try_up2 = try_up.up(j);
                    if (item2 == floor && item2 < 3 && try_up2.check()) {
                        chs.push_back(try_up2);
                    }
                }
            }
            if (item == floor && floor > 0 && item > 0) {
                State try_down = down(n-1).down(i);
                if (try_down.check()) {
                    chs.push_back(try_down);
                }
                for (int j=i+1; j<n-1; ++j) {
                    int item2 = get_floor(j);
                    State try_down2 = try_down.down(j);
                    if (item2 == floor && item2 > 0 && try_down2.check()) {
                        chs.push_back(try_down2);
                    }
                }
            }
        }
        return chs;
    }

    int n, s;
};

int main(int argc, char* argv[]) {
    State example1(5, 0x48);
    State example_success(5, 0x3ff);
    State input(11, 0x6666);
    State input_success(11, 0x3fffff);
    State input2(15, 0x6666);
    State input2_success(15, 0x3fffffff);

    // std::cout << "s0: " << example1 << " s1: " << example1.up(0);
    // std::cout << "s2: " << example1.up(1) << " sf: " << example1.up(example1.n-1) << std::endl;
    std::cout << "Example state: " << example1 << " to " << example_success << " in " << example1.solve(example_success) << " steps" << std::endl;
    std::cout << "Input state: " << input << " to " << input_success << " in " << input.solve(input_success) << " steps" << std::endl;
    std::cout << "Input2 state: " << input2 << " to " << input2_success << " in " << input2.solve(input2_success) << " steps" << std::endl;
    return 0;
}