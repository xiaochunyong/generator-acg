const Generator = require('yeoman-generator');
const chalk = require('chalk');
const yosay = require('yosay');
const path = require('path');
const mkdirp = require('mkdirp');
const utils = require('../utils');

module.exports = class extends Generator {
    initializing() {
        this.props = {
            template: 'spring-boot-with-kotlin'
        };
    }

    prompting() {
        // Have Yeoman greet the user.
        this.log(yosay(`Welcome to the beautiful ${chalk.red('generator-acg')} generator!`));

        const prompts = [
            // {
            //     type: 'input',
            //     name: 'namespace',
            //     message: 'Please input your project namespace,such as @xunlei:',
            //     default: ''
            // },
            {
                type: 'input',
                name: 'name',
                message: 'Please input project name:'
            },
            {
                type: 'input',
                name: 'description',
                message: 'Please input project description:'
            },
            // {
            //     type: 'input',
            //     name: 'main',
            //     message: 'Main file (index.js):',
            //     default: 'index.js'
            // },
            // {
            //     type: 'input',
            //     name: 'keywords',
            //     message: 'Package keywords (comma to split)',
            //     default: 'react,iceworks'
            // },
            // {
            //     type: 'input',
            //     name: 'template',
            //     message: 'Please input template name:',
            //     default: 'spring-boot-with-kotlin'
            // },
            // {
            //     type: 'input',
            //     name: 'uatPublicPath',
            //     message: 'Please input uat publicPath:',
            //     default: 'https://mtabc.aihuishou.com/uat/inspection/'
            // },
            // {
            //     type: 'input',
            //     name: 'proPublicPath',
            //     message: 'Please input pro publicPath:',
            //     default: 'https://mtabc.aihuishou.com/inspection/'
            // },
            {
                type: 'input',
                name: 'author',
                message: "Author's Name",
                default: 'Ely Xiao'
            },
            {
                type: 'input',
                name: 'email',
                message: '"Author\'s Email"',
                default: 'ely.xiao@aihuishou.com'
            },
            {
                type: 'input',
                name: 'repository',
                message: 'Project homepage url',
                default: ''
            },
            {
                type: 'input',
                name: 'homepage',
                message: '"Author\'s Homepage"',
                default: ''
            },
            {
                type: 'input',
                name: 'license',
                message: 'License',
                default: 'MIT'
            }
        ];

        return this.prompt(prompts).then(props => {
            // To access props later use this.props.someAnswer;
            this.props = { ...this.props, ...props };
            if (this.props.namespace) {
                this.props.fullName = `${this.props.namespace}/${this.props.name}`;
            } else {
                this.props.fullName = this.props.name;
            }
        });
    }

    default() {
        if (path.basename(this.destinationPath()) !== this.props.name) {
            this.log(`\nYour generator must be inside a folder named ${this.props.name}\n I will automatically create this folder.\n`);
            mkdirp(this.props.name);
            this.destinationRoot(this.destinationPath(this.props.name));
        }
    }

    writing() {
        this.log('\nWriting...\n');

        this._writingCommonFiles();
        this._writingSrc();
    }

    _writingCommonFiles() {
        const files = ['LICENCE', 'pom.xml', 'README.md.ejs'];
        files.forEach(file =>
            this.fs.copyTpl(this.templatePath(`${this.props.template}/${file}`), this.destinationPath(file), {
                name: this.props.name,
                fullName: this.props.fullName,
                author: this.props.author,
                license: this.props.license,
                camelCaseName: utils.getCamelCaseName(this.props.name),
                year: new Date().getFullYear()
            })
        );
    }

    _writingSrc() {
        this.fs.copy(
            this.templatePath(`${this.props.template}/spring-boot-with-kotlin-app`),
            this.destinationPath('spring-boot-with-kotlin-client')
        );
        this.fs.copy(
            this.templatePath(`${this.props.template}/spring-boot-with-kotlin-domain`),
            this.destinationPath('spring-boot-with-kotlin-domain')
        );
    }

    install() {
        this.log('\nInstall deps...\n');
        // this.installDependencies({ bower: false });
    }
};
