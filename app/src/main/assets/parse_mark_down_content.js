function parse_markdown_content(){
        var post_content_div_list_with_markdown=document.getElementsByClassName("md-read");
        /*var got_markdown=post_content_div_list_with_markdown.length;*/
        for(var i=0,got_markdown_l=post_content_div_list_with_markdown.length;i<got_markdown_l;i++){
            var now_div=post_content_div_list_with_markdown[i];
            if(!now_div.hasChildNodes())
                continue;
            /*get text box content;*/
            var original=now_div.children[0].textContent;

            var formal_div= document.createElement("div");
            formal_div.className="markdown-pulished";
            formal_div.innerHTML=marked(original);
            now_div.appendChild(formal_div);
        }
    };