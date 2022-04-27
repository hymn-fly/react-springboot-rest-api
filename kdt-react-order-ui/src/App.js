import './App.css'
import 'bootstrap/dist/css/bootstrap.css'
import React, {useEffect, useState} from "react";
import axios from "axios";

export function SummaryItem({productName, counts}) {
  return (
      <div className="row">
        <h6 className="p-0">{productName}<span className="badge bg-dark text-">{counts}개</span></h6>
      </div>
  )
}

export function Product(props) {
  const id = props.id;
  const productName = props.name;
  const categoryName = props.category;
  const price = props.price;

  const handleButtonOnClick = e => {
    props.onAddClick(id);
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
          <button onClick={handleButtonOnClick} className="btn btn-small btn-outline-dark">추가</button>
        </div>
      </>
  )
}

export function Summary({items = [], onOrderSubmit}) {
    const totalPrice = items.reduce((prev, curr) => prev + (curr.price * curr.count), 0)
    const [order, setOrder] = useState({
        email: "", address:"", postcode:""
    });
    const handleEmailInputChanged = (e) => {setOrder({...order, email: e.target.value});}
    const handleAddressInputChanged = (e) => {setOrder({...order, address: e.target.value});}
    const handlePostcodeInputChanged = (e) => {setOrder({...order, postcode: e.target.value});}
    const handleSubmit = (e) => {
        if (order.address === "" || order.email === "" || order.postcode === "") {
            alert("입력값을 확인해주세요!")
        } else {
            onOrderSubmit(order);
        }
    }

    return (
        <>
            <div>
                <h5 className="m-0 p-0"><b>Summary</b></h5>
            </div>
            <hr/>
            {items.map(v => <SummaryItem key={v.id} counts={v.count} productName={v.name}/>)}

            <form>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일</label>
                    <input type="email" className="form-control mb-1" value={order.email} onChange={handleEmailInputChanged} id="email"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="address" className="form-label">주소</label>
                    <input type="text" className="form-control mb-1" value={order.address} onChange={handleAddressInputChanged} id="address"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="postcode" className="form-label">우편번호</label>
                    <input type="text" className="form-control" value={order.postcode} onChange={handlePostcodeInputChanged} id="postcode"/>
                </div>
                <div>당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</div>
            </form>
            <div className="row pt-2 pb-2 border-top">
                <h5 className="col">총금액</h5>
                <h5 className="col text-end">{totalPrice}원</h5>
            </div>
            <button className="btn btn-dark col-12" onClick={handleSubmit}>결제하기</button>
        </>
    )
}

export function ProductList({products = [], onAddClick}) {

  return (
      <React.Fragment>
        <h5 className="flex-grow-0"><b>상품 목록</b></h5>
        <ul className="list-group products">
          {products.map(v =>
              <li key={v.id} className="list-group-item d-flex mt-3">
                <Product {...v} onAddClick={onAddClick}/>
              </li>
          )}
        </ul>
      </React.Fragment>
  )
}

function App() {
  const [products, setProducts] = useState([
    {id : 'uuid-1', name: '콜롬비아 커피1', category : '커피빈', price :3000},
    {id : 'uuid-2', name: '콜롬비아 커피2', category : '커피빈', price :3000},
    {id : 'uuid-3', name: '콜롬비아 커피3', category : '커피빈', price :3000},
  ]);

  const [items, setItems] = useState([
  ]);
  const handleAddClicked = id => {
    const product = products.find(v => v.id === id);
    const found = items.find(v => v.id === id);
    const updatedItems =
        found ? items.map(v => (v.id === id) ? {...v, count: v.count + 1} : v ) : [...items, { ...product, count:1 }]
    setItems(updatedItems);
    console.log(products.find(v => v.id === id), "added!");
  }
    const handleOrderSubmit = (order) => {
      if(items.length === 0){
          alert("아이템을 추가해 주세요");
      } else{
          axios.post("http://localhost:8080/api/v1/orders", {
              email : order.email,
              address: order.address,
              postcode: order.postcode,
              orderItems: items.map(v => ({
                      productId : v.id,
                      category : v.category,
                      price : v.price,
                      quantity : v.count,
                  }))
      }).then(
          v => alert("주문이 정상적으로 접수되었습니다."),
                  e => {
                      alert("서버 장애");
                      console.error(e);
          })
      }
      console.log(order, items);
    }

      useEffect(() => {
        axios.get("http://localhost:8080/api/v1/products")
            .then(v => setProducts(v.data));
      }, []);

  return (
      <div className="container-fluid">
        <div className="row justify-content-center m-4">
          <h1 className="text-center">Grids & Circle</h1>
        </div>
        <div className="card">
          <div className="row">
            <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
              <ProductList products={products} onAddClick={handleAddClicked}/>
            </div>
            <div className="col-md-4 summary p-4">
              <Summary items={items} onOrderSubmit={handleOrderSubmit}/>
            </div>
          </div>
        </div>
      </div>
  );
}

export default App;