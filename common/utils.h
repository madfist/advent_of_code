#ifndef COMMON_UTILS_H
#define COMMON_UTILS_H

void tokenize(const std::string& data, const std::string& delim, std::list<std::string>& l) {
  size_t pos = 0, last = 0;
  while ((pos = data.find_first_of(delim, last)) != std::string::npos) {
    // std::string step = data.substr(last, pos-last);
    // std::cout << "LAST " << last << " POS " << pos << " STEP " << step << std::endl;
    l.push_back(data.substr(last, pos-last));
    // std::cout << "T: " << l.back() << std::endl;
    last = pos + delim.length();
  }
  l.push_back(data.substr(last));
}

#endif