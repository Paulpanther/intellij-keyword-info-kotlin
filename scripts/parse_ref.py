import json
import urllib.request
from html.parser import HTMLParser


class RefHTMLParser(HTMLParser):
    base_url = 'https://kotlinlang.org/docs/'
    ul_h2_names = ['hard-keywords', 'soft-keywords', 'modifier-keywords']
    stop_name = 'special-identifiers'
    current_h2 = None
    in_ul = False
    in_li = False
    in_double_ul = False
    in_code = False
    current_text = ''
    current_keyword = None
    keywords = []

    def tag_to_text(self, tag, attrs):
        if tag == 'code':
            return '<code>'
        elif tag == 'a':
            ref = next(attr[1] for attr in attrs if attr[0] == 'href')
            return '<a href=\"' + self.base_url + ref + '\">'
        else:
            return ''

    def get_id(self, attrs):
        return next((attr[1] for attr in attrs if attr[0] == 'id'), None)

    def create_keyword(self):
        self.keywords.append({
            'name': self.current_keyword,
            'description': self.current_text,
            'elementType': self.current_keyword
        })
        self.current_text = ''

    def handle_starttag(self, tag, attrs):
        if self.in_double_ul:
            pass
        elif self.in_ul and tag == 'ul':
            self.in_double_ul = True
            self.in_li = False
        elif self.in_li or self.in_code:
            if tag == 'code':
                self.in_code = True
            self.current_text += self.tag_to_text(tag, attrs)
        elif self.in_ul and tag == 'li':
            self.in_li = True
        elif not self.in_ul and self.current_h2:
            if tag == 'h2':
                id_attr = self.get_id(attrs)
                if id_attr in self.ul_h2_names:
                    self.current_h2 = id_attr
                elif id_attr == self.stop_name:
                    self.current_h2 = None
            elif tag == 'ul':
                self.in_ul = True
        elif not self.current_h2 and tag == 'h2' and self.get_id(attrs) == self.ul_h2_names[0]:
            self.current_h2 = self.ul_h2_names[0]

    def handle_endtag(self, tag):
        if self.in_double_ul:
            if tag == 'ul':
                self.create_keyword()
                self.in_double_ul = False
        elif self.in_li and tag == 'li':
            self.create_keyword()
            self.in_li = False
        elif self.in_li:
            if tag == 'code':
                self.in_code = False
            if tag != 'p':
                self.current_text += '</' + tag + '>'
        elif self.in_ul and tag == 'ul':
            self.in_ul = False

    def handle_data(self, data):
        if self.in_code:
            self.current_keyword = data
        if self.in_li:
            self.current_text += data


def read_url(url):
    with urllib.request.urlopen(url) as page:
        return page.read().decode('utf8')


def write_json(data):
    with open('keywords.json', 'w') as f:
        json.dump(data, f, indent=2)


def main():
    url = 'https://kotlinlang.org/docs/keyword-reference.html'
    contents = read_url(url)
    parser = RefHTMLParser()
    parser.feed(contents)
    write_json(parser.keywords)


if __name__ == '__main__':
    main()
