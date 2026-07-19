const  capitalizeRegex = (str) =>{
    return str.replace(/\b\w/g, char => char.toUpperCase());
}

export { capitalizeRegex }