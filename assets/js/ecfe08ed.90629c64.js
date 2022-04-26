"use strict";(self.webpackChunk=self.webpackChunk||[]).push([[382],{3905:function(e,t,i){i.d(t,{Zo:function(){return c},kt:function(){return m}});var n=i(7294);function r(e,t,i){return t in e?Object.defineProperty(e,t,{value:i,enumerable:!0,configurable:!0,writable:!0}):e[t]=i,e}function o(e,t){var i=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),i.push.apply(i,n)}return i}function a(e){for(var t=1;t<arguments.length;t++){var i=null!=arguments[t]?arguments[t]:{};t%2?o(Object(i),!0).forEach((function(t){r(e,t,i[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(i)):o(Object(i)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(i,t))}))}return e}function s(e,t){if(null==e)return{};var i,n,r=function(e,t){if(null==e)return{};var i,n,r={},o=Object.keys(e);for(n=0;n<o.length;n++)i=o[n],t.indexOf(i)>=0||(r[i]=e[i]);return r}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(n=0;n<o.length;n++)i=o[n],t.indexOf(i)>=0||Object.prototype.propertyIsEnumerable.call(e,i)&&(r[i]=e[i])}return r}var l=n.createContext({}),u=function(e){var t=n.useContext(l),i=t;return e&&(i="function"==typeof e?e(t):a(a({},t),e)),i},c=function(e){var t=u(e.components);return n.createElement(l.Provider,{value:t},e.children)},p={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},d=n.forwardRef((function(e,t){var i=e.components,r=e.mdxType,o=e.originalType,l=e.parentName,c=s(e,["components","mdxType","originalType","parentName"]),d=u(i),m=r,h=d["".concat(l,".").concat(m)]||d[m]||p[m]||o;return i?n.createElement(h,a(a({ref:t},c),{},{components:i})):n.createElement(h,a({ref:t},c))}));function m(e,t){var i=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var o=i.length,a=new Array(o);a[0]=d;var s={};for(var l in t)hasOwnProperty.call(t,l)&&(s[l]=t[l]);s.originalType=e,s.mdxType="string"==typeof e?e:r,a[1]=s;for(var u=2;u<o;u++)a[u]=i[u];return n.createElement.apply(null,a)}return n.createElement.apply(null,i)}d.displayName="MDXCreateElement"},2770:function(e,t,i){i.r(t),i.d(t,{frontMatter:function(){return s},contentTitle:function(){return l},metadata:function(){return u},toc:function(){return c},default:function(){return d}});var n=i(7462),r=i(3366),o=(i(7294),i(3905)),a=["components"],s={id:"contributing",title:"Guidelines For Contributing",sidebar_label:"Guidelines"},l=void 0,u={unversionedId:"contributing",id:"contributing",isDocsHomePage:!1,title:"Guidelines For Contributing",description:"Before submitting an issue",source:"@site/../docs/contributing.md",sourceDirName:".",slug:"/contributing",permalink:"/openapi-style-validator/docs/contributing",editUrl:"https://github.com/OpenAPITools/openapi-style-validator/edit/master/website/../docs/contributing.md",tags:[],version:"current",lastUpdatedBy:"Jean-Fran\xe7ois C\xf4t\xe9",lastUpdatedAt:1650997072,formattedLastUpdatedAt:"4/26/2022",frontMatter:{id:"contributing",title:"Guidelines For Contributing",sidebar_label:"Guidelines"},sidebar:"docs",previous:{title:"Workflow Integrations",permalink:"/openapi-style-validator/docs/integrations"},next:{title:"Code of Conduct",permalink:"/openapi-style-validator/docs/code-of-conduct"}},c=[{value:"Before submitting an issue",id:"before-submitting-an-issue",children:[],level:2},{value:"Before submitting a PR",id:"before-submitting-a-pr",children:[],level:2},{value:"How to contribute",id:"how-to-contribute",children:[{value:"git",id:"git",children:[],level:3},{value:"Branches",id:"branches",children:[],level:3},{value:"Testing",id:"testing",children:[],level:3},{value:"Tips",id:"tips",children:[],level:3}],level:2}],p={toc:c};function d(e){var t=e.components,i=(0,r.Z)(e,a);return(0,o.kt)("wrapper",(0,n.Z)({},p,i,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("h2",{id:"before-submitting-an-issue"},"Before submitting an issue"),(0,o.kt)("ul",null,(0,o.kt)("li",{parentName:"ul"},"If you're not using the latest master to generate API clients or server stubs, please give it another try by pulling the latest master as the issue may have already been addressed."),(0,o.kt)("li",{parentName:"ul"},"Search the ",(0,o.kt)("a",{parentName:"li",href:"https://github.com/openapitools/openapi-style-validator/issues"},"open issue")," and ",(0,o.kt)("a",{parentName:"li",href:"https://github.com/openapitools/openapi-style-validator/issues?q=is%3Aissue+is%3Aclosed"},"closed issue")," to ensure no one else has reported something similar before."),(0,o.kt)("li",{parentName:"ul"},"File an ",(0,o.kt)("a",{parentName:"li",href:"https://github.com/openapitools/openapi-style-validator/issues/new"},"issue ticket")," by providing all the required information."),(0,o.kt)("li",{parentName:"ul"},"Test with the latest master by building the JAR locally to see if the issue has already been addressed."),(0,o.kt)("li",{parentName:"ul"},'You can also make a suggestion or ask a question by opening an "issue".')),(0,o.kt)("h2",{id:"before-submitting-a-pr"},"Before submitting a PR"),(0,o.kt)("ul",null,(0,o.kt)("li",{parentName:"ul"},"Search the ",(0,o.kt)("a",{parentName:"li",href:"https://github.com/openapitools/openapi-style-validator/issues"},"open issue")," to ensure no one else has reported something similar and no one is actively working on similar proposed change."),(0,o.kt)("li",{parentName:"ul"},"If no one has suggested something similar, open an ",(0,o.kt)("a",{parentName:"li",href:"https://github.com/openapitools/openapi-style-validator/issues"},'"issue"')," with your suggestion to gather feedback from the community."),(0,o.kt)("li",{parentName:"ul"},"It's recommended to ",(0,o.kt)("strong",{parentName:"li"},"create a new git branch")," for the change so that the merge commit message looks nicer in the commit history.")),(0,o.kt)("h2",{id:"how-to-contribute"},"How to contribute"),(0,o.kt)("h3",{id:"git"},"git"),(0,o.kt)("p",null,"If you're new to git, you may find the following FAQs useful:"),(0,o.kt)("p",null,(0,o.kt)("a",{parentName:"p",href:"https://github.com/openapitools/openapi-generator/wiki/FAQ#git"},"https://github.com/openapitools/openapi-generator/wiki/FAQ#git")),(0,o.kt)("h3",{id:"branches"},"Branches"),(0,o.kt)("p",null,"Please file the pull request against the correct branch. Most of the time it will be ",(0,o.kt)("inlineCode",{parentName:"p"},"master"),"."),(0,o.kt)("h3",{id:"testing"},"Testing"),(0,o.kt)("p",null,"It is not required but highly suggested that you create unit tests for the code you write. Also, make sure to run the existing tests before creating your PR."),(0,o.kt)("h3",{id:"tips"},"Tips"),(0,o.kt)("ul",null,(0,o.kt)("li",{parentName:"ul"},"Smaller changes are easier to review"),(0,o.kt)("li",{parentName:"ul"},"[Optional]"," For bug fixes, provide a OpenAPI Spec to repeat the issue so that the reviewer can use it to confirm the fix"),(0,o.kt)("li",{parentName:"ul"},"Add test case(s) to cover the change"),(0,o.kt)("li",{parentName:"ul"},"Make sure test cases passed after the change. All tests run after a PR is created or modified."),(0,o.kt)("li",{parentName:"ul"},"File a PR with meaningful title, description and commit messages."),(0,o.kt)("li",{parentName:"ul"},'To close an issue (e.g. issue 1542) automatically after a PR is merged, use keywords "fix", "close", "resolve" in the PR description, e.g. ',(0,o.kt)("inlineCode",{parentName:"li"},"fix #1542"),". (Ref: ",(0,o.kt)("a",{parentName:"li",href:"https://help.github.com/articles/closing-issues-using-keywords/"},"closing issues using keywords"),")")))}d.isMDXComponent=!0}}]);