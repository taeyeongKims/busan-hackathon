// user.route.js

import express from "express";
import asyncHandler from 'express-async-handler';

import { userSignin, userLogin, userAddLaw, userAddInterior, userAddCook, userAddClean, userAddStorage,
    userReadLaw, userReadInterior, userReadCook, userReadClean, userReadStorage } from "../controllers/user.controllers.js";
// userReadItem
export const userRouter = express.Router({mergeParams: true});


userRouter.post('/signin', asyncHandler(userSignin));
userRouter.post('/login', asyncHandler(userLogin));

userRouter.post('/law', asyncHandler(userAddLaw));
userRouter.post('/interior', asyncHandler(userAddInterior));
userRouter.post('/cook', asyncHandler(userAddCook));
userRouter.post('/clean', asyncHandler(userAddClean));
userRouter.post('/storage', asyncHandler(userAddStorage));

userRouter.get('/law/:lawTag', asyncHandler(userReadLaw));
userRouter.get('/interior', asyncHandler(userReadInterior));
userRouter.get('/cook', asyncHandler(userReadCook));
userRouter.get('/clean', asyncHandler(userReadClean));
userRouter.get('/storage', asyncHandler(userReadStorage));

//userRouter.get('/item', asyncHandler(userReadItem));


