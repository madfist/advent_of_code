#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>
#include "../include/utils.h"

typedef std::vector<char> ic_t;

ic_t read(std::istream& is) {
  char digit;
  ic_t image;
  while (is.get(digit)) {
    image.push_back(digit - 48);
  }
  return image;
}

template <class it>
int count_digit(it first, it last, int digit) {
  return std::accumulate(first, last, 0, [=](int acc, int v) {
    return acc + static_cast<int>(v == digit);
  });
}

int task1(const ic_t& image, int w, int h) {
  int n_layer = image.size() / w / h;
  int ret = 0;
  int min_zeros = w*h;
  for (int l = 0; l < n_layer; ++l) {
    int zeros = count_digit(image.begin() + l*w*h, image.begin() + (l+1)*w*h, 0);
    int ones = count_digit(image.begin() + l*w*h, image.begin() + (l+1)*w*h, 1);
    int twos = count_digit(image.begin() + l*w*h, image.begin() + (l+1)*w*h, 2);
    if (zeros < min_zeros) {
      min_zeros = zeros;
      ret = ones * twos;
    }
  }
  return ret;
}

ic_t task2(const ic_t& image, int w, int h) {
  ic_t ret(w*h);
  int n_layer = image.size() / w / h;
  for (auto i = 0; i != w*h; ++i) {
    for (int l = 0; l < n_layer; ++l) {
      char pixel = image[i + l*w*h];
      if (pixel != 2) {
        ret[i] = pixel;
        break;
      }
    }
  }
  return ret;
}

std::ostream& print_image(std::ostream& os, const ic_t& image, int w, int h) {
  for (int j = 0; j < h; ++j) {
    for (int i = 0; i < w; ++i) {
      os << ((image[i + j*w]) ? '#' : ' ');
    }
    os << '\n';
  }
  return os;
}

int main(int argc, char** argv) {
  if (argc < 3) {
    std::cout << "Usage: <input> |" << argv[0] << " <w> <h>\n";
    return 1;
  }

  ic_t image = read(std::cin);
  int w = atoi(argv[1]);
  int h = atoi(argv[2]);

  std::cout << task1(image, w, h) << '\n';
  print_image(std::cout, task2(image, w, h), w, h);

  return 0;
}
