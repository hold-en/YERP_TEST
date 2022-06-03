<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Kukudocs Editor</title>

    <script type="text/javascript" src="./public/externalLib/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="./public/externalLib/jquery-ui-1.11.4.min.js"></script>

    <script type="text/javascript" src="./public/javascripts/build/Editor.bundle.js"></script>
    <script type="text/javascript" src="./public/javascripts/build/OperationSetting.bundle.js"></script>

    <link rel="stylesheet" href="./public/stylesheets/style.css">

    <style>
      body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
    <script type="text/javascript">

        function Editor_Complete() {
            console.log("Editor Load Complete !!!");
        }

        function Key_event() {
            console.log("Key_event Call !!!");
        }

        function Mouse_event() {
            console.log("Mouse_event Call !!!");
        }

        $(document).ready(function(){
            window.kukuEditor = new KuKudocsEditor('editor', {
                minHeight : 0,
                maxHeight : 0,
                width : '100%',
                height : document.documentElement.clientHeight + 'px',
                fileUploadURL : '/Upload.jsp',
                publicPathURL: './public/',
                Editor_Complete : Editor_Complete
            });
        });
    </script>
  </head>

  <body>
    <textarea id="editor"><p><span>OnNovember3<a>0,2005, the High-Level</a> Committee of Management</span></p><div><span>test1</span><div><span>안녕하세요 반갑습니다1 </span></div></div><div><span>안녕하세요 반갑습니다 </span></div><span><hr id="test" class="kk_bookmark_view" kk_type="kk_type_bookmark" style="display:none;"></span><span>사이 텍스트<img src="./images/error.png">사이텍스트2</span><span><img src="./images/error.png"></span><span style="font-family:'arial'; font-weight: bold; color:purple; font-size:14px; text-decoration: underline line-through; font-style: italic; vertical-align: sub ;"> (HLCM) recommended that all United Nations system organizations adopt IPSAS as their accounting standardfor thepreparation of financial statementseffective no later than 2010.This rec</span><span class="">ommendation was driven by a clearly identifiedneed within the UN system to move to improved </span><p><span><a href="http://www.naver.com" target="_blank" title="URL Sample" id="urlHyperlinkID" class="urlHyperlinkClass" >IT development</a></span><span class="">IT development and modifications, and the <a href="#test">proposal</a> for this work was approved by the forty-sixth session of the Assemblies in December 2008. Although many UN organizations pushed back the original IPSAS implementation deadline, WIPO maintained the 2010 target date. As WIPO received an unqualified audit opinion for its 2010 financial statements, it became one of only nine UN organizations to adopt IPSAS by the originally planned date of January 1, 2010</span></p><table><colgroup><col style="width : 418px;"><col style="width : 418px;"></colgroup><tbody><tr><td style="border : 1px solid gray"><p><table><colgroup><col style="width : 418px;"><col style="width : 418px;"></colgroup><tbody><tr><td style="border : 1px solid gray"><p><span>  ANNUALFINANCIAL REPORT</span><span class=""> </span></p></td><td style="border : 1px solid gray"><p><span class=""> INT</span><span class="">RODUCTION </span></p></td><td style="border : 1px solid gray"><p><span class=""> INT</span><span class="">RODUCTION </span></p></td></tr><tr><td style="border : 1px solid gray"><p><span> STATEMENT I - Statement of </span><span class="">Financial Position</span><span> </span></p></td><td style="border : 1px solid gray"><p><span class=""> </span><span class="">ANNEX I – Statement of Financial Position by Business Unit [Unaudited]</span><span> </span></p></td><td style="border : 1px solid gray"><p><span> STATEMENT I - Statement of </span><span class="">Financial Position</span><span> </span></p></td></tr><tr><td style="border : 1px solid gray"><ol><li><span> STATEMENT I - Statement of Financial Position </span></li></ol></td><td style="border : 1px solid gray"><ol><li><span> STATEMENT II - Statement of Financial Position </span></li></ol></td><td style="border : 1px solid gray"><p><span> STATEMENT I - Statement of </span><span class="">Financial Position</span><span> </span></p></td></tr></tbody></table><span> ANNUALFINANCIAL REPORT</span><span class=""> </span></p></td><td style="border : 1px solid gray" rowspan="2"><p><span class=""> INT</span><span class="">RODUCTION </span></p></td><td style="border : 1px solid gray"><p><span class=""> INT</span><span class="">RODUCTION </span></p></td></tr><tr><td style="border : 1px solid gray"><p><span> STATEMENT I - Statement of </span><span class="">Financial Position</span><span> </span></p></td><td style="border : 1px solid gray"><p><span class=""> </span><span class="">ANNEX I – Statement of Financial Position by Business Unit [Unaudited]</span><span> </span></p></td></tr><tr><td style="border : 1px solid gray"><ol><li><span> STATEMENT I - Statement of Financial Position </span></li></ol></td><td style="border : 1px solid gray"><ol><li><span> STATEMENT II - Statement of Financial Position </span></li></ol></td><td style="border : 1px solid gray"><ol><li><span> STATEMENT II - Statement of Financial Position </span></li></ol></td></tr></tbody></table><ol><li><span>The composition of WIPO’s assets and liabilities remains broadly similar to 2011. Cash and cash equivalents total 408.1 million Swiss francs as at December 31, 2012, and represent 49.1per centof total assets. The Organization maintains significant investment in fixed assets, principally land, buildings, investment property and intangible </span></li><li><span>The principal liabilities of the Organization as at December 31, 2012 are payables and advance receipts of 303.0 million Swiss francs (representing 46.4per centof total liabilities), employee benefit liabilities (21.7per cent) and borrowings (22.9per cent). </span></li></ol></textarea>
  </body>
</html>
