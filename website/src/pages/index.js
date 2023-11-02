import React from 'react';
import classnames from 'classnames';
import Layout from '@theme/Layout';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import useBaseUrl from '@docusaurus/useBaseUrl';
import styles from './styles.module.css';
import CodeBlock from '@theme/CodeBlock'

const features = [
    {
        title: <>Easy to Use</>,
        imageUrl: `img/icons/plug.svg`,
        description: (
            <>
                <p>
                    OpenAPI Style Validator works out of the box with default validation that assure a consistent
                    specification.
                </p>
                <p>
                    Can easily be added to any java application with the library available on Maven.
                </p>
                <p>
                    Can also be added to your workflow with the CLI or the gradle plugin (Maven plugin coming soon!).
                </p>
                See <a href="./docs/integration">Integration</a> for details.
            </>
        ),
    },
    //TODO: Add more highlights as we go!
];


function stripMargin(template, ...expressions) {
    let result = template.reduce((accumulator, part, i) => {
        return accumulator + expressions[i - 1] + part
    });

    return result.replace(/\r?(\n)\s*\|/g, '$1');
}


const callouts = [
    {
        id: 'learn',
        title: <>Learn How</>,
        imageUrl: 'img/icons/newspaper-o.svg',
        content: (
            <div><span><p>OpenAPI Style Validator supports many different integrations and use cases, including (but not limited to):</p>
            <ul>
                <li>Gradle Plugin</li>
                <li>Jar CLI</li>
                <li>Jar library</li>
                <li>Maven Plugin (coming soon)</li>
            </ul>
            <p>For details, see  <a href="/docs/integrations">Workflow Integrations</a></p>
            </span></div>
        ),
    },
    {
        id: 'connectOnSlack',
        imageUrl: 'img/tools/Slack_Mark-256x256-3a29a6b.png',
        title: <>Active Community</>,
        content: (
            <>
                <p><strong>Connect</strong> with us on Slack!</p>
                <p>
                    We're a very community-oriented project. We have an active community of users, contributors, and
                    core team members on Slack. Slack is often a good
                    place to start if you're looking for guidance about where to begin contributing, if you have an idea
                    you're
                    not sure fits the project, or if you just want to ask a question or say hello.
                </p>
                <p>Slack is free to <a href="https://slack.com/downloads" className="href">download</a>, and our
                    workspace is free to <a
                        href="https://join.slack.com/t/openapi-generator/shared_invite/enQtNzAyNDMyOTU0OTE1LTY5ZDBiNDI5NzI5ZjQ1Y2E5OWVjMjZkYzY1ZGM2MWQ4YWFjMzcyNDY5MGI4NjQxNDBiMTlmZTc5NjY2ZTQ5MGM"
                        className="href">sign up</a>.
                </p>
            </>
        ),
    },
];

function Callout({id, title, imageUrl, content}) {
    const imgUrl = useBaseUrl(imageUrl);
    let alt = `${id} logo`;

    return (
        <>
            <div id={id} className={classnames('row', styles.calloutRow)}>
                <div className={classnames('col col--3 blockImage')}>
                    <img className={'image'} src={imgUrl} alt={alt}/>
                </div>
                <div className={'col col--9'}>
                    <h2>{title}</h2>
                    {content}
                </div>
            </div>
        </>
    )
}

function Feature({imageUrl, title, description}) {
    const imgUrl = useBaseUrl(imageUrl);
    return (
        <div className={classnames('col col--6', styles.feature)}>
            {imgUrl && (
                <div className="text--center">
                    <img className={styles.featureImage} src={imgUrl} alt="logo"/>
                </div>
            )}
            <h3>{title}</h3>
            <div>{description}</div>
        </div>
    );
}

function ShowcaseUser({image, infoLink, caption}) {
    const imgUrl = useBaseUrl(image);
    return (
        <a href={infoLink} key={infoLink}>
            <img src={imgUrl} alt={caption} title={caption} className={styles.productShowcaseSectionLogo}/>
        </a>
    );
}
function Showcase({users}) {
    const context = useDocusaurusContext();
    const {siteConfig = {}} = context;
    return (
        <>
            <h2>Who is Using This?</h2>
            <p>Here are some users of OpenAPI Generator</p>
            <div className="logos">
                {users.filter(user => user.pinned).map((user, idx) => (
                    <ShowcaseUser key={idx} {...user} />
                ))}
            </div>
            <div className="more-users">
                <Link
                    className={classnames(
                        'button button--outline button--primary button--lg',
                        styles.productShowcaseSectionButton
                    )}
                    to={useBaseUrl('users')}>
                    More {siteConfig.title} Users
                </Link>
            </div>
        </>
    );
}

function Home() {
    const context = useDocusaurusContext();
    const {siteConfig = {}} = context;
    const {users = {}} = siteConfig.customFields;

    return (
        <Layout
            title={`Hello from ${siteConfig.title}`}
            description="Description will go into a meta tag in <head />">
            <header className={classnames('hero hero--primary', styles.heroBanner)}>
                <div className="container">
                    <h1 className="hero__title">{siteConfig.title}</h1>
                    <img src="img/logo.png" />
                    <p className="hero__subtitle">{siteConfig.tagline}</p>
                    <div className={styles.buttons}>
                        <Link
                            className={classnames(
                                'button button--outline button--secondary button--lg',
                                styles.getStarted,
                            )}
                            to={useBaseUrl('#try')}>
                            Try It Out
                        </Link>
                        <Link
                            className={classnames(
                                'button button--outline button--secondary button--lg',
                                styles.getStarted,
                            )}
                            to={useBaseUrl('docs/installation')}>
                            Install
                        </Link>
                    </div>

                    <div className={styles.buttons}>
                        <Link
                            className={classnames(
                                'button button--outline button--secondary button--md',
                                styles.getStarted,
                            )}
                            to={useBaseUrl('docs/integrations')}>
                            Integrations
                        </Link>
                    </div>
                </div>
            </header>
            <div className={classnames(styles.announcement, styles.announcementDark)}>
                <div className={styles.announcementInner}>
                    <h2><b>Sponsors</b></h2>
                    <h3>Gold Sponsors</h3>
                    <p><a href="https://www.devmark.ai/fern/?utm_source=openapitools-openapistylevalidator&utm_loc=website&utm_type=logo"><img src="img/sponsors/fern_logo_tagline.svp" /></a></p>
                    <p>If you find OpenAPI Style Generator useful, please consider <a
                        href="https://github.com/OpenAPITools/openapi-style-validator"> becoming a developer's sponsor
                    </a> by clicking on the "Sponsor" button on the top of the page.</p>
                    <p>You can also sponsor the organization directly by <a
                        href="https://opencollective.com/openapi_generator">becoming a backer</a>.</p>
                </div>
            </div>
            <main>
                {features && features.length && (
                    <section className={styles.features}>
                        <div className="features container">
                            <div className="row">
                                {features.map((props, idx) => (
                                    <Feature key={idx} {...props} />
                                ))}
                            </div>
                        </div>
                    </section>
                )}

                {callouts && callouts.length && (
                    <section className={styles.callout}>
                        <div className="callout container">
                            {callouts.map((props, idx) => (
                                <Callout key={idx} {...props} />
                            ))}
                        </div>
                    </section>
                )}
            </main>

            {users && users.length && (
                <div className={classnames(styles.announcement, styles.announcementLight)}>
                    <div className={classnames(styles.productShowcaseSection, styles.announcementInner)}>
                        <Showcase users={users}/>
                    </div>
                </div>
            )}

            <div className={classnames(styles.announcement, styles.announcementDark)}>
                "OpenAPI Tools" and "OpenAPI Generator" are not affiliated with OpenAPI Initiative (OAI)
            </div>
        </Layout>
    );
}

export default Home;
