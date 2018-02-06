<?php

namespace App\Http\Controllers;

use App\User;
use App\KategoriUser;
use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;
use App\Http\Requests\LoginRequest;
use App\Http\Requests\RegisterRequest;
use App\Http\Resources\UserResource;

class UserController extends Controller
{

    public function __construct()
    {
      $this->middleware('auth:api')->except(['login', 'register', 'logout']);
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        if (!Auth::guard()->user()->isAdmin) {
          return response()->json(['message' => 'method not allowed'], 403);
        }

        return UserResource::collection(User::where('kategori_user','member')->get());
    }

    public function UKMCollection()
    {
      if (!Auth::guard()->user()->isAdmin) {
        return response()->json(['message' => 'method not allowed'], 403);
      }

      return UserResource::collection(User::where('kategori_user','ukm')->get());
    }

    public function MMCollection()
    {
      if (!Auth::guard()->user()->isAdmin) {
        return response()->json(['message' => 'method not allowed'], 403);
      }

      return UserResource::collection(User::where('kategori_user','makanan-minuman')->get());
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function profile()
    {
      $user = User::where('username',Auth::guard()->user()->username)->first();
      return new UserResource($user);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function register(RegisterRequest $request, User $user)
    {
      $user->nama = $request->input('nama');
      $user->username = $request->input('username');
      $user->api_token = str_random(60);
      $user->password  = bcrypt($request->input('password'));
      $user->phone = $request->input('phone');
      $user->rekening = str_random(8);
      $user->saldo = 0;
      $user->isOfficial = 0;
      $user->kategori_user = "member";

      return $user->save() ? new UserResource($user) : response()->json(['status'=> 'register failed'], 422);
    }

    /*
    * store official account
    *
    */
    public function official(Request $request, User $user)
    {
      $user->nama = $request->input('nama');
      $user->username = $request->input('username');
      $user->password  = bcrypt(str_slug($request->input('nama')));
      $user->api_token = str_random(60);
      $user->phone = $request->input('phone');
      $user->rekening = str_random(8);
      $user->saldo = 0;
      $user->isOfficial = 1;
      $user->kategori_user = "makanan-minuman";

      if ($user->save()) return new UserResource($user);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function login(LoginRequest $request)
    {
      if (Auth::attempt(['username' => $request->input('username'), 'password' => $request->input('password')]))
      {
        $user = Auth::guard()->user();
        $user->api_token = str_random(60);
        $user->save();

        return new UserResource($user);
      }
      else
      {
        return response()->json(['status' => 'Incorrect auth'], 404);
      }
    }


    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
      $user = User::findOrFail($request->id);

      $user->nama = $request->nama ?? $user->nama;
      $user->username = $request->input('username') ?? $user->username;
      $user->password  = bcrypt($request->input('password')) ?? $user->password;
      $user->phone = $request->input('phone') ?? $user->phone;

      if ($user->save()) return new UserResource($user);

    }

    public function logout(){
      $user = Auth::guard('api')->user();

      if ($user) {
          $user->api_token = null;
          $user->save();
      }

      return response()->json(['message' => 'user logged out.'], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $user = User::where('username',$id)->first();

        if ($user->delete()) return new UserResource($user);
    }
}
