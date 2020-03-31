(function (w) {
    var s = {insideIframe: false}

    $(iframe).mouseenter(function () {
        s.insideIframe = true;
        s.scrollX = w.scrollX;
        s.scrollY = w.scrollY;
    }).mouseleave(function () {
        s.insideIframe = false;
    });

    $(document).scroll(function () {
        if (s.insideIframe)
            w.scrollTo(s.scrollX, s.scrollY);
    });
})(window);