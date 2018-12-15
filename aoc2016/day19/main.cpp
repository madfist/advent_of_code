#include <iostream>

struct Elf {
    Elf(int i) : id(i), next(nullptr) {}
    int id;
    Elf *next;
};

Elf* fill(int n) {
    Elf *start = new Elf(1);
    Elf *e = start;
    for (int i=2; i<=n; ++i) {
        e->next = new Elf(i);
        e = e->next;
    }
    e->next = start;
    return start;
}

int game1(Elf* e) {
    while (e != e->next) {
        Elf *d = e->next;
        e->next = d->next;
        delete d;
        e = e->next;
    }
    int id = e->id;
    delete e;
    return id;
}

int game2(Elf* e, int n) {
    Elf *h = e;
    for (int i=1; i<n/2; ++i) {
        h = h->next;
    }
    while (e != e->next) {
        Elf *d = h->next;
        h->next = h->next->next;
        delete d;
        if (n%2 == 1) {
            h = h->next;
        }
        e = e->next;
        n--;
    }
    int id = e->id;
    delete e;
    return id;
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        std::cout << "Usage: " << argv[0] << "<n_elf>" << std::endl;
        return 1;
    }
    int n = std::atoi(argv[1]);
    Elf *e = fill(n);
    std::cout << "Game 1: " << game1(e) << std::endl;
    e = fill(n);
    std::cout << "Game 2: " << game2(e, n) << std::endl;
    return 0;
}
