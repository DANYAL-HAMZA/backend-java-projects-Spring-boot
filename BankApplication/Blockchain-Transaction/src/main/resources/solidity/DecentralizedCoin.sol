// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;

import {ERC20} from "./ERC20.sol";
import {ERC20Burnable} from "./ERC20Burnable.sol";
import {Ownable} from "./Ownable.sol";



  contract DecentralizedCoin is ERC20, ERC20Burnable, Ownable {

       error DecentralizedStableCoin__AmountMustBeMoreThanZero();
       error DecentralizedStableCoin__BurnAmountExceedsBalance();
       error DecentralizedStableCoin__NotZeroAddress();

        constructor() ERC20("DANYAL", "DAN") Ownable(msg.sender) {}

        function burn(address from, uint256 _amount) public  onlyOwner returns(bool){
        uint256 balance = balanceOf(from);
        if (_amount <= 0) {
        revert DecentralizedStableCoin__AmountMustBeMoreThanZero();
        }
        if (balance < _amount) {
        revert DecentralizedStableCoin__BurnAmountExceedsBalance();
        }
        super._burn(from, _amount);
         return true;
        }

        function mint(address _to, uint256 _amount) external onlyOwner returns (bool) {
        if (_to == address(0)) {
        revert DecentralizedStableCoin__NotZeroAddress();
        }
        if (_amount <= 0) {
        revert DecentralizedStableCoin__AmountMustBeMoreThanZero();
        }
        _mint(_to, _amount);
        return true;
        }


        }




