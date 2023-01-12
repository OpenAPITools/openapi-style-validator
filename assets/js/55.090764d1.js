(self.webpackChunk=self.webpackChunk||[]).push([[55],{9055:function(e,t,n){"use strict";n.d(t,{Z:function(){return _}});var o=n(7462),r=n(7294),l=n(6010),a={plain:{backgroundColor:"#2a2734",color:"#9a86fd"},styles:[{types:["comment","prolog","doctype","cdata","punctuation"],style:{color:"#6c6783"}},{types:["namespace"],style:{opacity:.7}},{types:["tag","operator","number"],style:{color:"#e09142"}},{types:["property","function"],style:{color:"#9a86fd"}},{types:["tag-id","selector","atrule-id"],style:{color:"#eeebff"}},{types:["attr-name"],style:{color:"#c4b9fe"}},{types:["boolean","string","entity","url","attr-value","keyword","control","directive","unit","statement","regex","at-rule","placeholder","variable"],style:{color:"#ffcc99"}},{types:["deleted"],style:{textDecorationLine:"line-through"}},{types:["inserted"],style:{textDecorationLine:"underline"}},{types:["italic"],style:{fontStyle:"italic"}},{types:["important","bold"],style:{fontWeight:"bold"}},{types:["important"],style:{color:"#c4b9fe"}}]},s={Prism:n(7410).default,theme:a};function c(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function i(){return i=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var o in n)Object.prototype.hasOwnProperty.call(n,o)&&(e[o]=n[o])}return e},i.apply(this,arguments)}var p=/\r\n|\r|\n/,u=function(e){0===e.length?e.push({types:["plain"],content:"\n",empty:!0}):1===e.length&&""===e[0].content&&(e[0].content="\n",e[0].empty=!0)},y=function(e,t){var n=e.length;return n>0&&e[n-1]===t?e:e.concat(t)},d=function(e,t){var n=e.plain,o=Object.create(null),r=e.styles.reduce((function(e,n){var o=n.languages,r=n.style;return o&&!o.includes(t)||n.types.forEach((function(t){var n=i({},e[t],r);e[t]=n})),e}),o);return r.root=n,r.plain=i({},n,{backgroundColor:null}),r};function g(e,t){var n={};for(var o in e)Object.prototype.hasOwnProperty.call(e,o)&&-1===t.indexOf(o)&&(n[o]=e[o]);return n}var h=function(e){function t(){for(var t=this,n=[],o=arguments.length;o--;)n[o]=arguments[o];e.apply(this,n),c(this,"getThemeDict",(function(e){if(void 0!==t.themeDict&&e.theme===t.prevTheme&&e.language===t.prevLanguage)return t.themeDict;t.prevTheme=e.theme,t.prevLanguage=e.language;var n=e.theme?d(e.theme,e.language):void 0;return t.themeDict=n})),c(this,"getLineProps",(function(e){var n=e.key,o=e.className,r=e.style,l=i({},g(e,["key","className","style","line"]),{className:"token-line",style:void 0,key:void 0}),a=t.getThemeDict(t.props);return void 0!==a&&(l.style=a.plain),void 0!==r&&(l.style=void 0!==l.style?i({},l.style,r):r),void 0!==n&&(l.key=n),o&&(l.className+=" "+o),l})),c(this,"getStyleForToken",(function(e){var n=e.types,o=e.empty,r=n.length,l=t.getThemeDict(t.props);if(void 0!==l){if(1===r&&"plain"===n[0])return o?{display:"inline-block"}:void 0;if(1===r&&!o)return l[n[0]];var a=o?{display:"inline-block"}:{},s=n.map((function(e){return l[e]}));return Object.assign.apply(Object,[a].concat(s))}})),c(this,"getTokenProps",(function(e){var n=e.key,o=e.className,r=e.style,l=e.token,a=i({},g(e,["key","className","style","token"]),{className:"token "+l.types.join(" "),children:l.content,style:t.getStyleForToken(l),key:void 0});return void 0!==r&&(a.style=void 0!==a.style?i({},a.style,r):r),void 0!==n&&(a.key=n),o&&(a.className+=" "+o),a})),c(this,"tokenize",(function(e,t,n,o){var r={code:t,grammar:n,language:o,tokens:[]};e.hooks.run("before-tokenize",r);var l=r.tokens=e.tokenize(r.code,r.grammar,r.language);return e.hooks.run("after-tokenize",r),l}))}return e&&(t.__proto__=e),t.prototype=Object.create(e&&e.prototype),t.prototype.constructor=t,t.prototype.render=function(){var e=this.props,t=e.Prism,n=e.language,o=e.code,r=e.children,l=this.getThemeDict(this.props),a=t.languages[n];return r({tokens:function(e){for(var t=[[]],n=[e],o=[0],r=[e.length],l=0,a=0,s=[],c=[s];a>-1;){for(;(l=o[a]++)<r[a];){var i=void 0,d=t[a],g=n[a][l];if("string"==typeof g?(d=a>0?d:["plain"],i=g):(d=y(d,g.type),g.alias&&(d=y(d,g.alias)),i=g.content),"string"==typeof i){var h=i.split(p),m=h.length;s.push({types:d,content:h[0]});for(var f=1;f<m;f++)u(s),c.push(s=[]),s.push({types:d,content:h[f]})}else a++,t.push(d),n.push(i),o.push(0),r.push(i.length)}a--,t.pop(),n.pop(),o.pop(),r.pop()}return u(s),c}(void 0!==a?this.tokenize(t,o,a,n):[o]),className:"prism-code language-"+n,style:void 0!==l?l.root:{},getLineProps:this.getLineProps,getTokenProps:this.getTokenProps})},t}(r.Component),m=h;var f=n(7594),v=n.n(f),b={plain:{color:"#bfc7d5",backgroundColor:"#292d3e"},styles:[{types:["comment"],style:{color:"rgb(105, 112, 152)",fontStyle:"italic"}},{types:["string","inserted"],style:{color:"rgb(195, 232, 141)"}},{types:["number"],style:{color:"rgb(247, 140, 108)"}},{types:["builtin","char","constant","function"],style:{color:"rgb(130, 170, 255)"}},{types:["punctuation","selector"],style:{color:"rgb(199, 146, 234)"}},{types:["variable"],style:{color:"rgb(191, 199, 213)"}},{types:["class-name","attr-name"],style:{color:"rgb(255, 203, 107)"}},{types:["tag","deleted"],style:{color:"rgb(255, 85, 114)"}},{types:["operator"],style:{color:"rgb(137, 221, 255)"}},{types:["boolean"],style:{color:"rgb(255, 88, 116)"}},{types:["keyword"],style:{fontStyle:"italic"}},{types:["doctype"],style:{color:"rgb(199, 146, 234)",fontStyle:"italic"}},{types:["namespace"],style:{color:"rgb(178, 204, 214)"}},{types:["url"],style:{color:"rgb(221, 221, 221)"}}]},k=n(5350),j=n(2822),x=function(){var e=(0,j.LU)().prism,t=(0,k.Z)().isDarkTheme,n=e.theme||b,o=e.darkTheme||n;return t?o:n},E=n(5999),N="codeBlockContainer_K1bP",T="codeBlockContent_hGly",C="codeBlockTitle_eoMF",B="codeBlock_23N8",L="copyButton_Ue-o",P="codeBlockLines_39YC",S=/{([\d,-]+)}/,O=["js","jsBlock","jsx","python","html"],Z={js:{start:"\\/\\/",end:""},jsBlock:{start:"\\/\\*",end:"\\*\\/"},jsx:{start:"\\{\\s*\\/\\*",end:"\\*\\/\\s*\\}"},python:{start:"#",end:""},html:{start:"\x3c!--",end:"--\x3e"}},w=["highlight-next-line","highlight-start","highlight-end"],D=function(e){void 0===e&&(e=O);var t=e.map((function(e){var t=Z[e],n=t.start,o=t.end;return"(?:"+n+"\\s*("+w.join("|")+")\\s*"+o+")"})).join("|");return new RegExp("^\\s*(?:"+t+")\\s*$")};function _(e){var t=e.children,n=e.className,a=e.metastring,c=e.title,i=(0,j.LU)().prism,p=(0,r.useState)(!1),u=p[0],y=p[1],d=(0,r.useState)(!1),g=d[0],h=d[1];(0,r.useEffect)((function(){h(!0)}),[]);var f=(0,j.bc)(a)||c,b=(0,r.useRef)(null),k=[],O=x(),Z=Array.isArray(t)?t.join(""):t;if(a&&S.test(a)){var w=a.match(S)[1];k=v()(w).filter((function(e){return e>0}))}var _=null==n?void 0:n.split(" ").find((function(e){return e.startsWith("language-")})),A=null==_?void 0:_.replace(/language-/,"");!A&&i.defaultLanguage&&(A=i.defaultLanguage);var z=Z.replace(/\n$/,"");if(0===k.length&&void 0!==A){for(var I,R="",$=function(e){switch(e){case"js":case"javascript":case"ts":case"typescript":return D(["js","jsBlock"]);case"jsx":case"tsx":return D(["js","jsBlock","jsx"]);case"html":return D(["js","jsBlock","html"]);case"python":case"py":return D(["python"]);default:return D()}}(A),F=Z.replace(/\n$/,"").split("\n"),U=0;U<F.length;){var W=U+1,G=F[U].match($);if(null!==G){switch(G.slice(1).reduce((function(e,t){return e||t}),void 0)){case"highlight-next-line":R+=W+",";break;case"highlight-start":I=W;break;case"highlight-end":R+=I+"-"+(W-1)+","}F.splice(U,1)}else U+=1}k=v()(R),z=F.join("\n")}var K=function(){!function(e,t){var n=(void 0===t?{}:t).target,o=void 0===n?document.body:n,r=document.createElement("textarea"),l=document.activeElement;r.value=e,r.setAttribute("readonly",""),r.style.contain="strict",r.style.position="absolute",r.style.left="-9999px",r.style.fontSize="12pt";var a=document.getSelection(),s=!1;a.rangeCount>0&&(s=a.getRangeAt(0)),o.append(r),r.select(),r.selectionStart=0,r.selectionEnd=e.length;var c=!1;try{c=document.execCommand("copy")}catch(i){}r.remove(),s&&(a.removeAllRanges(),a.addRange(s)),l&&l.focus()}(z),y(!0),setTimeout((function(){return y(!1)}),2e3)};return r.createElement(m,(0,o.Z)({},s,{key:String(g),theme:O,code:z,language:A}),(function(e){var t=e.className,a=e.style,s=e.tokens,c=e.getLineProps,i=e.getTokenProps;return r.createElement("div",{className:(0,l.Z)(N,null==n?void 0:n.replace(/language-[^ ]+/,""))},f&&r.createElement("div",{style:a,className:C},f),r.createElement("div",{className:(0,l.Z)(T,A)},r.createElement("pre",{tabIndex:0,className:(0,l.Z)(t,B,"thin-scrollbar"),style:a},r.createElement("code",{className:P},s.map((function(e,t){1===e.length&&"\n"===e[0].content&&(e[0].content="");var n=c({line:e,key:t});return k.includes(t+1)&&(n.className+=" docusaurus-highlight-code-line"),r.createElement("span",(0,o.Z)({key:t},n),e.map((function(e,t){return r.createElement("span",(0,o.Z)({key:t},i({token:e,key:t})))})),r.createElement("br",null))})))),r.createElement("button",{ref:b,type:"button","aria-label":(0,E.I)({id:"theme.CodeBlock.copyButtonAriaLabel",message:"Copy code to clipboard",description:"The ARIA label for copy code blocks button"}),className:(0,l.Z)(L,"clean-btn"),onClick:K},u?r.createElement(E.Z,{id:"theme.CodeBlock.copied",description:"The copied button label on code blocks"},"Copied"):r.createElement(E.Z,{id:"theme.CodeBlock.copy",description:"The copy button label on code blocks"},"Copy"))))}))}},7594:function(e,t){function n(e){let t,n=[];for(let o of e.split(",").map((e=>e.trim())))if(/^-?\d+$/.test(o))n.push(parseInt(o,10));else if(t=o.match(/^(-?\d+)(-|\.\.\.?|\u2025|\u2026|\u22EF)(-?\d+)$/)){let[e,o,r,l]=t;if(o&&l){o=parseInt(o),l=parseInt(l);const e=o<l?1:-1;"-"!==r&&".."!==r&&"\u2025"!==r||(l+=e);for(let t=o;t!==l;t+=e)n.push(t)}}return n}t.default=n,e.exports=n}}]);