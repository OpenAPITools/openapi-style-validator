const fs = require('fs');
const path = require('path');
const parseYaml = require("js-yaml").safeLoad;

const loadYaml = relativePath => parseYaml(fs.readFileSync(path.join(__dirname, relativePath), "utf8"));

// See https://docusaurus.io/docs/site-config for all the possible
// site configuration options.

const team = loadYaml("src/dynamic/team.yml");
const users = loadYaml("src/dynamic/users.yml");
const baseUrl = '/openapi-style-validator/';

const docusaurusConfig = {
  title: 'OpenAPI Style Validator',
  tagline: 'A customizable style validator to make sure your OpenAPI spec follows your organization\'s standards.',
  url: 'https://openapitools.github.io/openapi-style-validator/', // Your website URL
  baseUrl: '/', // Base URL for your project */
  favicon: 'img/favicon.png',
  organizationName: 'OpenAPITools',
  projectName: 'openapi-style-validator',
  trailingSlash: true,

  // // You may provide arbitrary config keys to be used as needed by your
  // // template. For example, if you need your repo's URL...
  // repoUrl: 'https://github.com/OpenAPITools/openapi-generator',

  themeConfig: {
    // Open Graph and Twitter card images.
    image: 'img/docusaurus.png',

    sidebarCollapsible: true,

    prism: {
      theme: require('prism-react-renderer/themes/dracula'),
      defaultLanguage: 'bash',
    },

    navbar: {
      title: 'OpenAPI Style Validator',
      logo: {
        src: 'img/logo.png',
        alt: 'OpenAPI Style Validator logo',
      },

      items: [
        {to: 'docs/installation', label: 'Install'},
        {to: 'docs/roadmap', label: 'Roadmap'},
        {to: "docs/faq", label: "FAQ" },
        {to: "team", label: "Team" },
      ],
    },

    footer: {
      style: 'dark',

      logo: {
        alt: 'OpenAPI Tools',
        src: 'img/logo.png',
        href: 'https://openapi-generator.tech/',
      },

      copyright:  `Copyright © ${new Date().getFullYear()} OpenAPI-Style-Validator Contributors (https://openapitools.github.io/openapi-style-validator/)`,
      links: [
        {
          title: 'Docs',
          items: [
            {
              label: 'Installation',
              to: 'docs/installation',
            },
            {
              label: 'Workflow Integrations',
              to: 'docs/integrations',
            },
          ],
        },
        {
          title: 'Community',
          items: [
            {
              label: 'User Showcase',
              to: 'users',
            },
            {
              label: 'Stack Overflow',
              href: 'https://stackoverflow.com/questions/tagged/openapi-style-validator',
            },
            {
              label: 'Chat Room',
              href: 'https://join.slack.com/t/openapi-generator/shared_invite/enQtNzAyNDMyOTU0OTE1LTY5ZDBiNDI5NzI5ZjQ1Y2E5OWVjMjZkYzY1ZGM2MWQ4YWFjMzcyNDY5MGI4NjQxNDBiMTlmZTc5NjY2ZTQ5MGM',
            },
          ],
        },
        {
          title: 'More',
          items: [
            {
              label: 'GitHub',
              href: 'https://github.com/OpenAPITools/openapi-style-validator',
            },
          ],
        },
      ]
    },
  },
  presets: [
    [
      '@docusaurus/preset-classic',
      {
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },

        docs: {
          // docs folder path relative to website dir.
          path: '../docs',
          include: ['**/*.md', '**/*.mdx'],

          // sidebars file relative to website dir.
          sidebarPath: require.resolve('./sidebars.js'),

          /**
           * Theme components used by the docs pages
           */
          docLayoutComponent: '@theme/DocPage',
          docItemComponent: '@theme/DocItem',

          editUrl: 'https://github.com/OpenAPITools/openapi-style-validator/edit/master/website',

          // Equivalent to `docsUrl`.
          routeBasePath: 'docs',
          // Remark and Rehype plugins passed to MDX. Replaces `markdownOptions` and `markdownPlugins`.
          remarkPlugins: [],
          rehypePlugins: [],
          // Equivalent to `enableUpdateBy`.
          showLastUpdateAuthor: true,
          // Equivalent to `enableUpdateTime`.
          showLastUpdateTime: true,
        },
      },
    ],
  ],

  // Add custom scripts here that would be placed in <script> tags.
  scripts: [
      'https://buttons.github.io/buttons.js',
      'https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/2.0.0/clipboard.min.js',
      `${baseUrl}js/code-block-buttons.js`,
  ],
  customFields: {
    users: users,
    team: team
  },
};

module.exports = docusaurusConfig;
