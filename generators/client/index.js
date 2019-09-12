const Generator = require('yeoman-generator');
const chalk = require('chalk');
const yosay = require('yosay');
const path = require('path');
const mkdirp = require('mkdirp');
const user = require('yeoman-generator/lib/actions/user');
const utils = require('../utils');

module.exports = class extends Generator {
    initializing() {
        this.props = {
            template: 'iceworks'
        };
    }

    prompting() {
        // Have Yeoman greet the user.
        this.log(yosay(`Welcome to the beautiful ${chalk.red('generator-acg')} generator!`));

        const prompts = [
            {
                type: 'input',
                name: 'namespace',
                message: 'Please input your project namespace,such as @aihuishou:',
                default: '@creative'
            },
            {
                type: 'input',
                name: 'name',
                message: 'Please input project name:'
                // default: `jdy-fe-${Math.random()}`
            },
            {
                type: 'input',
                name: 'description',
                message: 'Please input project description:'
                // default: `desc-${Math.random()}`
            },
            {
                type: 'input',
                name: 'version',
                message: 'Please input project version:',
                default: '1.0.0'
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
            //     default: 'iceworks'
            // },
            {
                type: 'input',
                name: 'uatPublicPath',
                message: 'Please input uat publicPath (like "https://mybucket.oss-cn-hangzhou.aliyuncs.com/uat/myproject":'
                // default: 'https://mtabc.aihuishou.com/uat/inspection/'
                // default: 'https://mybucket.oss-cn-hangzhou.aliyuncs.com/uat/myproject'
            },
            {
                type: 'input',
                name: 'proPublicPath',
                message: 'Please input pro publicPath (like "https://mybucket.oss-cn-hangzhou.aliyuncs.com/myproject":'
                // default: 'https://mtabc.aihuishou.com/inspection/'
            },
            {
                type: 'input',
                name: 'uatEndpoint',
                message: 'Please input uat server api endpoint (like "https://uatapi.xxx.com"):',
                // default: 'https://uat007.aihuishou.com'
                // default: 'https://uat<project>.company.com'
            },
            {
                type: 'input',
                name: 'proEndpoint',
                message: 'Please input pro server api endpoint (like "https://api.xxx.com"):',
                // default: 'https://007.aihuishou.com'
                default: ''
            },
            {
                type: 'input',
                name: 'author',
                message: "Author's Name",
                default: user.git.name
            },
            {
                type: 'input',
                name: 'email',
                message: "Author's Email",
                default: user.git.email
            }
            // {
            //     type: 'input',
            //     name: 'repository',
            //     message: 'Project homepage url',
            //     default: ''
            // },
            // {
            //     type: 'input',
            //     name: 'homepage',
            //     message: "Author's Homepage",
            //     default: ''
            // },
            // {
            //     type: 'input',
            //     name: 'license',
            //     message: 'License',
            //     default: 'MIT'
            // }
        ];

        return this.prompt(prompts).then(props => {
            // To access props later use this.props.someAnswer;
            this.props = {
                ...this.props,
                ...props,
                camelCaseName: utils.getCamelCaseName(props.name),
                year: new Date().getFullYear()
            };
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
        this._writingPublic();
        this._writingOtherFiles();
    }

    _writingCommonFiles() {
        const files = [
            '.editorconfig',
            '.eslintignore',
            '.eslintrc.js',
            '.gitignore.ejs',
            '.stylelintignore',
            '.stylelintrc.js',
            'CHANGELOG.md',
            'ice.config.js.ejs',
            'jsconfig.json',
            'package.json.ejs',
            'package-lock.json',
            'README.md.ejs'
        ];
        files.forEach(file =>
            this.fs.copyTpl(this.templatePath(`${this.props.template}/${file}`), this.destinationPath(file.replace('.ejs', '')), this.props)
        );
    }

    _writingOtherFiles() {
        const files = ['src/apis/apiGenerator.js.ejs'];
        files.forEach(file => {
            this.fs.delete(this.destinationPath(file));
            this.fs.copyTpl(
                this.templatePath(`${this.props.template}/${file}`),
                this.destinationPath(file.replace('.ejs', '')),
                this.props
            );
        });
    }

    _writingSrc() {
        this.fs.copy(this.templatePath(`${this.props.template}/src`), this.destinationPath('src'));
    }

    _writingPublic() {
        this.fs.copy(this.templatePath(`${this.props.template}/public`), this.destinationPath('public'));
    }

    install() {
        this.log('\nInstall deps...\n');
        this.installDependencies({ bower: false });
    }
};
