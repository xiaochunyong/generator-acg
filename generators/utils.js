module.exports = {
    getCamelCaseName
};

function getCamelCaseName(name) {
    if (name.indexOf('-')) {
        const tempName = name.toLowerCase().split('-');

        for (let i = 1; i < tempName.length; i++) {
            tempName[i] = tempName[i].substring(0, 1).toUpperCase() + tempName[i].substring(1);
        }

        return tempName.join('');
    }
    return name;
}
