package ru.kesva.makechoice

class JsonTestStrings {
    companion object {
        const val responseJsonString = """
    
{
  "kind": "customsearch#search",
  "url": {
    "type": "application/json",
    "template": "https://www.googleapis.com/customsearch/v1?q={searchTerms}&num={count?}&start={startIndex?}&lr={language?}&safe={safe?}&cx={cx?}&sort={sort?}&filter={filter?}&gl={gl?}&cr={cr?}&googlehost={googleHost?}&c2coff={disableCnTwTranslation?}&hq={hq?}&hl={hl?}&siteSearch={siteSearch?}&siteSearchFilter={siteSearchFilter?}&exactTerms={exactTerms?}&excludeTerms={excludeTerms?}&linkSite={linkSite?}&orTerms={orTerms?}&relatedSite={relatedSite?}&dateRestrict={dateRestrict?}&lowRange={lowRange?}&highRange={highRange?}&searchType={searchType}&fileType={fileType?}&rights={rights?}&imgSize={imgSize?}&imgType={imgType?}&imgColorType={imgColorType?}&imgDominantColor={imgDominantColor?}&alt=json"
  },
  "queries": {
    "request": [
      {
        "title": "Google Custom Search - кошка",
        "totalResults": "7850000000",
        "searchTerms": "кошка",
        "count": 1,
        "startIndex": 1,
        "inputEncoding": "utf8",
        "outputEncoding": "utf8",
        "safe": "off",
        "cx": "3f8ce6301b45b1e05",
        "hl": "ru",
        "searchType": "image",
        "imgType": "photo"
      }
    ],
    "nextPage": [
      {
        "title": "Google Custom Search - кошка",
        "totalResults": "7850000000",
        "searchTerms": "кошка",
        "count": 1,
        "startIndex": 2,
        "inputEncoding": "utf8",
        "outputEncoding": "utf8",
        "safe": "off",
        "cx": "3f8ce6301b45b1e05",
        "hl": "ru",
        "searchType": "image",
        "imgType": "photo"
      }
    ]
  },
  "context": {
    "title": "Поиск картинок"
  },
  "searchInformation": {
    "searchTime": 0.965562,
    "formattedSearchTime": "0.97",
    "totalResults": "7850000000",
    "formattedTotalResults": "7,850,000,000"
  },
  "items": [
    {
      "kind": "customsearch#result",
      "title": "Почему нам кажется, что кошки недружелюбны и высокомерны - BBC ...",
      "htmlTitle": "Почему нам кажется, что \u003cb\u003eкошки\u003c/b\u003e недружелюбны и высокомерны - BBC ...",
      "link": "http://c.files.bbci.co.uk/CD5E/production/_109447525_catsmaincoonunfriendly.jpg",
      "displayLink": "www.bbc.com",
      "snippet": "Почему нам кажется, что кошки недружелюбны и высокомерны - BBC ...",
      "htmlSnippet": "Почему нам кажется, что \u003cb\u003eкошки\u003c/b\u003e недружелюбны и высокомерны - BBC ...",
      "mime": "image/jpeg",
      "fileFormat": "image/jpeg",
      "image": {
        "contextLink": "https://www.bbc.com/russian/vert-fut-50223070",
        "height": 1152,
        "width": 2048,
        "byteSize": 479460,
        "thumbnailLink": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTv7jgdjvvfDihabC7KdMMo7F5BG892do3BbKkCFCgfKEA1s7usfi8PZA&s",
        "thumbnailHeight": 84,
        "thumbnailWidth": 150
      }
    }
  ]
}
    
"""
    }
}