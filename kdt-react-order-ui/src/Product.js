import React from "@types/react";


export function Product(props) {
    const id = props.id;
    const productName = props.name;
    const categoryName = props.category;
    const price = props.price;

    const handleButtonOnClick = e => {
        console.log(id, "clicked!")
    };

    return (
        <>
            <div className="col-2"><img className="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg" alt=""/></div>
            <div className="col">
                <div className="row text-muted">{categoryName}</div>
                <div className="row">{productName}</div>
            </div>
            <div className="col text-center price">{price}원</div>
            <div className="col text-end action">
                <button onClick={handleButtonOnClick} className="btn btn-small btn-outline-dark" href="">추가</button>
            </div>
        </>
    )

}